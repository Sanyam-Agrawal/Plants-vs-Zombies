import javafx.stage.*;
import javafx.event.*;
import javafx.scene.image.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.animation.*; 
import javafx.util.Duration;

import java.util.*;

public class Game
{
    private BorderPane root;
    private double mouseX, mouseY;
    private int score=0,progress=0;
    private Button l_score;
    public Stage stage;
    public App myapp;
    private Player player;
    private LevelMenu level_menu;
    private Scene level_menu_scene;
    private ArrayList<ImageView> plantmenuimageviews = new ArrayList<>();
    private ArrayList<Image> plantmenuimages = new ArrayList<>();
    private ArrayList<Image> plantmenublurredimages = new ArrayList<>();

    final double HOUSE_LAST_LINE = 250;
    final double RIGHTMOST_LINE  = 1100;

    public boolean isPaused = false;

    public Game(App app,Stage stage,String name)
    {
        this.myapp=app;
        this.stage=stage;
        this.player=new Player(name,this);
        this.level_menu=new LevelMenu(app,stage);
        System.out.println(level_menu);
    }

    public Scene getLevelMenuScene()
    {
        if(level_menu_scene==null)
            this.level_menu_scene=level_menu.createScene();
        return this.level_menu_scene;
    }

    public Scene createScene()
    {
        root = new BorderPane();
        VBox gameVBox = new VBox(10.0);

        Button game_menu = new Button("MENU");
        game_menu.setFont(Font.font("Serif", FontWeight.BOLD, 24));
        game_menu.setStyle("-fx-background-color: #1E001E; -fx-text-fill: #FFFFFF");
        game_menu.setMaxWidth(120);
        game_menu.setTranslateX(980);
        EventHandler<ActionEvent> game_menu_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                {
                    stage.setScene(myapp.getGameMenuScene());
                } 
            };
        game_menu.setOnAction(game_menu_event);
        gameVBox.getChildren().add(game_menu);

        root.getChildren().add(this.displayScore());

        root.setLeft(gameVBox);

        ArrayList<Row> rows = new ArrayList<>();
        for (int i=0; i<5; ++i)
        {
            rows.add( new Row(95 + i*100) );
            VBox mower;
            while ((mower = rows.get(i).getLawnMower().getVBox()) == null);
            root.getChildren().add(mower);
            mower.setTranslateX(180);
            mower.setTranslateY(rows.get(i).getMiddle());
        }

        long[] plantAvailable = new long[5];
        long start_time = System.nanoTime();
        for (int i=0; i<5; ++i) plantAvailable[i] = start_time;

        long[] timeNeeded = {5000000000L, 5000000000L, 10000000000L, 15000000000L, 25000000000L};

        Timeline mainTimer = new Timeline (new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (isPaused) return;

                long curr_time = System.nanoTime();

                for (int i=0; i<5; ++i)
                {
                    if ((curr_time-plantAvailable[i]) < timeNeeded[i])
                        plantmenuimageviews.get(i).setImage(plantmenublurredimages.get(i));
                    else
                        plantmenuimageviews.get(i).setImage(plantmenuimages.get(i));
                }

                l_score.setText(""+score);

                for (Row row : rows)
                {
                 handlePlants(row);
                 handlePeas(row);
                 handleLawnMowers(row);
                 handleZombies(row);
                }
            }
        }));

        mainTimer.setCycleCount(Timeline.INDEFINITE);
        mainTimer.play();

        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) {
                if (!isPaused) root.getChildren().add(createSun());
            }
        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

        Scene scene=new Scene(root,1100,600);
        scene.getStylesheets().add(Game.class.getResource("game.css").toExternalForm());

        int[] middle_point = {(260+340)/2, (340+415)/2, (415+505)/2, (505+580)/2, (580+665)/2, (665+750)/2,
                (750+820)/2, (820+910)/2, (910+1000)/2};

        ArrayList<Integer> plantSelected = new ArrayList<>();
        plantSelected.add(-1);

        Cursor default_cursor = scene.getCursor();

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (isPaused) return;

                mouseX = event.getSceneX();
                mouseY = event.getSceneY();

                // System.out.println(mouseX + "+" + mouseY);

                if (plantSelected.get(0)==-1)
                {
                    if (0<=mouseX && mouseX<=120 && 0<=mouseY && mouseY<=490)
                    {
                        long curr_time = System.nanoTime();
                        int which_plant = (int)(mouseY/100);
                        if ((curr_time-plantAvailable[which_plant]) >= timeNeeded[which_plant])
                        {
                            plantAvailable[which_plant] = curr_time;
                            plantSelected.set(0,which_plant);
                            scene.setCursor(new ImageCursor(plantmenuimages.get(which_plant)));
                        }
                    }
                }
                else
                {
                    if (HOUSE_LAST_LINE<=mouseX && mouseX<=1000 && 80<=mouseY && mouseY<=600)
                    {
                        int row_no = find_row_no(mouseY);
                        int column_no = find_column_no(mouseX);
                        if (rows.get(row_no).isColumnOkay(column_no))
                        {
                            Plants plant = null;
                            switch(plantSelected.get(0))
                            {
                                case 0: plant = new SunFlower(); break;
                                case 1: plant = new PeaShooter(); break;
                                case 2: plant = new FreezePeaShooter(); break;
                                case 3: plant = new Wallnut(); break;
                                case 4: plant = new CherryBomb(); break;
                            }

                            if (plant==null) return;

                            VBox p = rows.get(row_no).addPlant(plant,column_no);
                            p.setTranslateX(middle_point[column_no]-40);
                            root.getChildren().add(p);
                            plantSelected.set(0,-1);
                            scene.setCursor(default_cursor);
                        }
                    }
                    else if (0<=mouseX && mouseX<=120 && 0<=mouseY && mouseY<=490)
                    {
                        plantAvailable[plantSelected.get(0)] = 0;
                        plantSelected.set(0,-1);
                        scene.setCursor(default_cursor);
                    }
                }
            }
        });

        VBox plant_chooser = createPlantMenu();
        root.getChildren().add(plant_chooser);



        return scene;
    }

    private void gameover(boolean winStatus)
    {
        //???
    }

    void handlePlants(Row row)
    {
        Map<Plants,Integer> plants = row.getPlants();

        for (Iterator<Map.Entry<Plants,Integer>> i = plants.entrySet().iterator(); i.hasNext(); )
        { 
            Map.Entry<Plants,Integer> entry = i.next(); 

            Plants plant = entry.getKey();

            if (plant.getHealth() <= 0)
            {
                plant.getVBox().getChildren().clear();
                row.markEmpty(entry.getValue());
                i.remove();
            }
        }
    }

    void handlePeas(Row row)
    {
        Set<Peas> peas = row.getPeas();
        boolean stillThere = false;

        for (Iterator<Peas> i = peas.iterator(); i.hasNext(); )
        {
            Peas pea = i.next();

            for (Zombies zombie : row.getZombies())
            {
                if (pea.getVBox().getBoundsInParent().intersects(zombie.getVBox().getBoundsInParent()))
                {
                    zombie.decreaseHealth(pea.getAttack());
                    if (pea.isFreezing()) zombie.freeze();
            
                    pea.getVBox().getChildren().clear();
                    i.remove();
                
                    stillThere = false;
                    break;
                }
            }

            if (stillThere) pea.getVBox().setLayoutX(pea.getVBox().getLayoutX() + pea.getXdelta());
        }
    }

    void handleLawnMowers(Row row)
    {
        LawnMower lawnmower = row.getLawnMower();
        boolean moveLM = false;

        for (Zombies zombie : row.getZombies())
        {
            if (lawnmower==null && zombie.getVBox().getLayoutX()<=HOUSE_LAST_LINE)
            {
                gameover(false);
                return;
            }

            if (lawnmower!=null && lawnmower.getVBox().getBoundsInParent().intersects(zombie.getVBox().getBoundsInParent()))
            {
                zombie.decreaseHealth(10000);
                moveLM = true;
            }
        }

        if (moveLM) lawnmower.getVBox().setLayoutX(lawnmower.getVBox().getLayoutX() + lawnmower.getXdelta());

        if (lawnmower!=null && lawnmower.getVBox().getLayoutX() >= RIGHTMOST_LINE)
        {
            lawnmower.getVBox().getChildren().clear();
            row.removeLawnMower();
        }
    }

    void handleZombies(Row row)
    {
        Set<Zombies> zombies = row.getZombies();

        for (Iterator<Zombies> i = zombies.iterator(); i.hasNext(); )
        {
            Zombies zombie = i.next();

            if (zombie.getHealth() <= 0)
            {
                zombie.getVBox().getChildren().clear();
                i.remove();
                continue;
            }

            for (Map.Entry<Plants,Integer> plant_c : row.getPlants().entrySet())
            {
                Plants plant = plant_c.getKey();

                if (zombie.getVBox().getBoundsInParent().intersects(plant.getVBox().getBoundsInParent()))
                {
                    plant.decreaseHealth(zombie.getAttack());
                    break;
                }
            }

            zombie.getVBox().setLayoutX(zombie.getVBox().getLayoutX() + zombie.getXdelta());
        }
    }

    public boolean[] getAvailabelLevels()
    {
        return player.getAvailabelLevels();
    }

    private int find_row_no (double y)
    {
        if (80<=y && y<180) return 0;
        if (180<=y && y<280) return 1;
        if (280<=y && y<390) return 2;
        if (390<=y && y<480) return 3;
        return 4;
    }

    private int find_column_no (double x)
    {
        if (260<=x && x<340) return 0;
        if (340<=x && x<415) return 1;
        if (415<=x && x<505) return 2;
        if (505<=x && x<580) return 3;
        if (580<=x && x<665) return 4;
        if (665<=x && x<750) return 5;
        if (750<=x && x<820) return 6;
        if (820<=x && x<910) return 7;
        return 8;
    }

    public HBox displayScore()
    {
        Button redundant = new Button("whatever");
        redundant.setDisable(true);
        redundant.setVisible(false);

        Image image_s = new Image("shovel.png");
        ImageView view_image_s= new ImageView(image_s);
        view_image_s.setFitHeight(75);
        view_image_s.setFitWidth(75);

        HBox scoreboard=new HBox();
        scoreboard.setSpacing(40);
        scoreboard.setMaxWidth(900.0);
        scoreboard.setMaxHeight(500.0);
        Image image = new Image("sun.png");
        ImageView view_image= new ImageView(image);

        this.score=50;
        l_score=new Button(Integer.toString(this.score));
        l_score.setFont(Font.font("Serif", FontWeight.EXTRA_BOLD, 30));
        l_score.setStyle("-fx-background-radius: 4em;" + "-fx-background-color: #99ffcc;");
        l_score.setMinWidth(125);
        l_score.setTranslateY(10);
        l_score.setTranslateX(-50);

        this.progress=54;
        Button b_progress=new Button("Progress = "+this.progress+"%");
        b_progress.setFont(Font.font("Serif", FontWeight.BOLD, 15));
        b_progress.setStyle("-fx-text-fill: #0000ff; -fx-background-color: #99ffcc;");
        b_progress.setMinWidth(200);
        b_progress.setTranslateY(20);
        b_progress.setTranslateX(-50);

        scoreboard.getChildren().add(view_image_s);
        scoreboard.getChildren().add(view_image);
        scoreboard.getChildren().add(l_score);
        scoreboard.getChildren().add(b_progress);
        scoreboard.setTranslateX(400);
        scoreboard.setTranslateY(-10);
        return scoreboard;
    }

    public VBox createSun()
    {
        VBox sun = new VBox(50);
        sun.setSpacing(40);
        sun.setMaxWidth(250.0);
        sun.setMaxHeight(400.0);
        Image image = new Image("sun.png");
        ImageView view_image= new ImageView(image);
        sun.getChildren().add(view_image);
        TranslateTransition move_sun =new TranslateTransition();
        move_sun.setDuration(Duration.millis(12000)); 
        move_sun.setNode(view_image); 

        move_sun.setByY(1100); 
        move_sun.setCycleCount(1); 
        move_sun.setAutoReverse(false); 
        move_sun.setOnFinished(e->sun.getChildren().clear());
        sun.setTranslateX(200+800*Math.random());
        sun.setTranslateY(10);
        move_sun.play();

        sun.setOnMouseClicked(event -> 
            {
                score+=25;    
                move_sun.stop();
                sun.getChildren().clear();
            }
        );
        return sun;
    }

    public void produceSun(int x,int y)
    {
        VBox sun = new VBox(50);
        sun.setSpacing(40);
        sun.setMaxWidth(250.0);
        sun.setMaxHeight(400.0);
        Image image = new Image("sun.png");
        ImageView view_image= new ImageView(image);
        sun.getChildren().add(view_image);
        sun.setOnMouseClicked(event -> 
            {
                score+=25;    
                sun.getChildren().clear();
            }
        );
        sun.setTranslateX(x);
        sun.setTranslateY(y);
        root.getChildren().add(sun);
    }

    private VBox createPlantMenu()
    {
        VBox menu = new VBox(50);
        menu.setSpacing(0);
        // menu.setMaxWidth(100);
        // menu.setMaxHeight(75);
        menu.setMaxWidth(180);
        menu.setMaxHeight(70);

        String[] resources_paths = {"card_sunflower.png", "card_peashooter.png", "card_freezepeashooter.png",
                "card_wallnut.png", "card_cherrybomb.png"};

        for (String s: resources_paths)
        { 
            Image image = new Image(getClass().getResourceAsStream("blurred_"+s));
            ImageView imageView=new ImageView(image);
            imageView.setFitHeight(100);
            // imageView.setFitWidth(75);
            imageView.setFitWidth(120);
            plantmenuimageviews.add(imageView);
            plantmenublurredimages.add(image);
            plantmenuimages.add(new Image(getClass().getResourceAsStream(s)));
            menu.getChildren().add(imageView);
        }

        return menu;
    }
}