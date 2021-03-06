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
import javafx.scene.media.*;
import java.io.*;
import java.util.*;

public class Game
{
    private BorderPane root;
    private double mouseX, mouseY;
    private Button l_score, b_progress;
    public Stage stage;
    public App myapp;
    private ArrayList<ImageView> plantmenuimageviews = new ArrayList<>();
    private ArrayList<Image> plantmenuimages = new ArrayList<>();
    private ArrayList<Image> plantmenublurredimages = new ArrayList<>();
    private TranslateTransition move_sun;
    private ArrayList<Integer> plantSelected;
    private ArrayList<Row> rows;
    private int middle_point[];
    private Map<Timeline,ArrayList<Boolean>> allTimelines = new HashMap<>();
    private boolean[] plantUnlocked;
    private Player player;
    private Level level;
    private int level_no;

    private AudioClip peasound = new AudioClip("file:firepea.mp3");
    private AudioClip wave_sound = new AudioClip("file:hugewave.mp3");

    final double HOUSE_LAST_LINE = 250;
    final double RIGHTMOST_LINE  = 1100;

    public boolean isPaused = false;

    public Game()
    {

    }

    public Game(App app,Stage stage,Player player)
    {
        this.myapp=app;
        this.stage=stage;
        this.player=player;
        level_no = -1;
    }

    public int getLevelNo() { return this.level_no; }

    private boolean holdingShovel = false;

    public Scene createScene(Player player,int level_no)
    {
        this.player = player;
        level = player.getLevel(level_no);
        this.level_no = level_no;
        this.plantUnlocked = player.getPlantUnlocked();

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
                    isPaused = true;
                    stage.setScene(myapp.getGameMenuScene());
                } 
            };
        game_menu.setOnAction(game_menu_event);
        gameVBox.getChildren().add(game_menu);

        root.getChildren().add(this.displayScore());

        root.setLeft(gameVBox);

        rows = level.getRows();
        for (int i=0; i<5; ++i)
        {
            if (rows.get(i).getLawnMower() != null)
            {    
                rows.get(i).getLawnMower().resetVBox();
                VBox mower = rows.get(i).getLawnMower().getVBox();
                root.getChildren().add(mower);
            }

            for (Map.Entry<Plants,Integer> plant_m : rows.get(i).getPlants().entrySet())
            {
                Plants plant = plant_m.getKey();

                plant.resetVBox();
                root.getChildren().add(plant.getVBox());

                if (plant instanceof PeaShooter)
                    createPeaShooter(plant,false,i,plant_m.getValue());
                else if (plant instanceof FreezePeaShooter)
                    createPeaShooter(plant,true,i,plant_m.getValue());
                else if (plant instanceof SunFlower)
                    createSunFlowerAni(plant,i,((SunFlower)plant).getColumn_no());
            }

            for (Peas pea : rows.get(i).getPeas())
            {
                pea.resetVBox();
                root.getChildren().add(pea.getVBox());
            }

            for (Zombies zombie : rows.get(i).getZombies())
            {
                zombie.resetVBox();
                root.getChildren().add(zombie.getVBox());
            }
        }

        Timeline sungen = new Timeline(new KeyFrame(Duration.seconds(10), new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event) {
                            root.getChildren().add(createSun());
                        }
                    }));
        sungen.setCycleCount(Timeline.INDEFINITE);
        sungen.play();

        long[] plantAvailable = level.getTimes();

        long[] timeNeeded = {5000000000L, 5000000000L, 10000000000L, 15000000000L, 25000000000L};
        int[] cost = {50,100,175,50,150};

        Timeline mainTimer = new Timeline (new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event)
                        {
                            if (isPaused)
                            {
                                sungen.pause();
                                if(move_sun!=null) move_sun.pause();
                                return;
                            }

                            if(move_sun!=null) move_sun.play();
                            sungen.play();

                            long curr_time = System.nanoTime();

                            for (int i=0; i<5; ++i)
                            {
                                if (!plantUnlocked[i] || (curr_time-plantAvailable[i]) < timeNeeded[i] || level.score < cost[i])
                                    plantmenuimageviews.get(i).setImage(plantmenublurredimages.get(i));
                                else
                                    plantmenuimageviews.get(i).setImage(plantmenuimages.get(i));
                            }

                            l_score.setText(""+level.score);
                            b_progress.setText(""+level.getProgress());

                            for (Row row : rows)
                            {
                                handlePlants(row);
                                handlePeas(row);
                                handleLawnMowers(row);
                                handleZombies(row);
                            }

                            if (level.waveFinished()) gameover(true);
                        }
                    }));

        mainTimer.setCycleCount(Timeline.INDEFINITE);
        mainTimer.play();

        Scene scene=new Scene(root,1100,600);
        scene.getStylesheets().add(Game.class.getResource("game.css").toExternalForm());

        int[] random_array = {(260+340)/2, (340+415)/2, (415+505)/2, (505+580)/2, (580+665)/2, (665+750)/2,
                (750+820)/2, (820+910)/2, (910+1000)/2};
        middle_point = random_array;
         
        plantSelected = new ArrayList<>();
        plantSelected.add(-1);

        Cursor default_cursor = scene.getCursor();

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    if (isPaused) return;

                    mouseX = event.getSceneX();
                    mouseY = event.getSceneY();

                    if (holdingShovel)
                    {
                        if (HOUSE_LAST_LINE<=mouseX && mouseX<=1000 && 80<=mouseY && mouseY<=600)
                        {
                            int row_no = find_row_no(mouseY);
                            int column_no = find_column_no(mouseX);

                            if (!rows.get(row_no).isColumnOkay(column_no))
                            {
                                rows.get(row_no).markEmpty(column_no);
                                holdingShovel = false;
                            }
                        }

                        return;
                    }

                    if (plantSelected.get(0)==-1)
                    {
                        if (0<=mouseX && mouseX<=120 && 0<=mouseY && mouseY<=490)
                        {
                            long curr_time = System.nanoTime();
                            int which_plant = (int)(mouseY/100);
                            if (plantUnlocked[which_plant] &&
                                (curr_time-plantAvailable[which_plant]) >= timeNeeded[which_plant] &&
                                level.score>=cost[which_plant])
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
                            int which_plant = plantSelected.get(0);

                            if (rows.get(row_no).isColumnOkay(column_no))
                            {
                                Plants plant = null;
                                switch(which_plant)
                                {
                                    case 0: plant = new SunFlower(column_no); break;
                                    case 1: plant = new PeaShooter(); break;
                                    case 2: plant = new FreezePeaShooter(); break;
                                    case 3: plant = new Wallnut(); break;
                                    case 4: plant = new CherryBomb(); break;
                                }

                                if (plant==null) return;

                                level.score -= cost[which_plant];

                                VBox p = rows.get(row_no).addPlant(plant,column_no);
                                p.setTranslateX(middle_point[column_no]-40);
                                plant.xpos = middle_point[column_no]-40;
                                root.getChildren().add(p);
                                scene.setCursor(default_cursor);

                                if (which_plant==1 || which_plant==2)
                                {
                                    Plants plan = plant;
                                    boolean flag = (which_plant==2);
                                    createPeaShooter(plan,flag,row_no,column_no);
                                }
                                else if (which_plant==0)
                                {
                                    createSunFlowerAni(plant,row_no,column_no);
                                }
                                else if (which_plant==4)
                                {
                                    Plants plan = plant;
                                    Timeline blast = new Timeline(new KeyFrame(Duration.millis(600), new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            p.getChildren().clear();
                                            p.getChildren().add(new ImageView(new Image("Boom.gif")));

                                            int[] row_nos = {row_no-1, row_no, row_no+1};
                                            for (Integer r : row_nos)
                                            {
                                            	if (0<=r && r<=4)
                                            	{
	                                            	Set<Zombies> zombies = rows.get(r).getZombies();
	                                                for (Iterator<Zombies> i = zombies.iterator(); i.hasNext(); )
	                                                {
	                                                    Zombies zombie = i.next();
	                                                    if (Math.abs(zombie.getVBox().getTranslateX() - middle_point[column_no]) < 150)
	                                                        zombie.decreaseHealth(10000);
	                                                }
	                                            }    
                                            }
                                            
                                            root.getChildren().remove(p); rows.get(row_no).getPlants().remove(plan);
                                        }
                                    }));
                                    blast.setCycleCount(1);
                                    blast.play();
                                }

                                plantSelected.set(0,-1);
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

        Random r = new Random();

        Timeline zomgen = new Timeline(new KeyFrame(Duration.millis(level.getFrequency()), new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event) {
                            if (isPaused || !level.spawn()) return;
                            int row_no = r.nextInt(5);
                            root.getChildren().add(rows.get(row_no).spawnZombie(level.getSeverity()));
                        }
                    }));
        zomgen.setCycleCount(Timeline.INDEFINITE);
        zomgen.play();

        boolean[] wavedone = {false};

        Timeline finalwave = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event) {
                            if (isPaused || !level.readyForFinalWave() || wavedone[0]) return;
                            wavedone[0] = true;

                            wave_sound.play();
                            VBox v=new VBox(10);
                            Label l=new Label("Final Wave is Coming!!!");
                            l.setFont(new Font(60.0));
                            l.setTextFill(Color.web("#ff0000", 0.8));
                            l.setStyle("-fx-color: red");
                            l.setMinWidth(800);
                            l.setMinHeight(800);
                            v.setTranslateX(300);
                            v.setTranslateY(-300);
                            v.getChildren().add(l);
                            root.getChildren().add(v);
                            Timeline text_time = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>()
                                        {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                root.getChildren().remove(v);

                                            }}));
                            text_time.setCycleCount(1);
                            text_time.play();

                            for (int i=0; i<5; ++i)
                            {
                                int no = i;
                                Timeline generate = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>()
                                {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        if (isPaused || !level.waveRemaining(no)) return;
                                        root.getChildren().add(rows.get(no).spawnZombie(level.getSeverity()));
                                    }
                                }));
                                generate.setCycleCount(Timeline.INDEFINITE);
                                generate.play();
                            }
                        }
                    }));
        finalwave.setCycleCount(Timeline.INDEFINITE);
        finalwave.play();        

        Timeline gc = new Timeline(new KeyFrame(Duration.millis(2000), new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event) {
                            for (Iterator<Map.Entry<Timeline,ArrayList<Boolean>>> i = allTimelines.entrySet().iterator(); i.hasNext(); )
                            {
                                Map.Entry<Timeline,ArrayList<Boolean>> entry = i.next(); 
                                if (entry.getValue().get(0))
                                {
                                    entry.getKey().stop();
                                    i.remove();
                                }
                            }
                        }
                    }));
        gc.setCycleCount(Timeline.INDEFINITE);
        gc.play();

        VBox shovel = new VBox(50);
        shovel.setSpacing(40);
        shovel.setMaxWidth(250.0);
        shovel.setMaxHeight(400.0);
        Image image_s = new Image("shovel_bg_black.png");
        ImageView view_image_s= new ImageView(image_s);
        view_image_s.setFitHeight(75);
        view_image_s.setFitWidth(75);
        shovel.getChildren().add(view_image_s);
        shovel.setTranslateX(300);
        shovel.setOnMouseClicked(event -> { if (plantSelected.get(0)==-1) holdingShovel = !holdingShovel; });
        root.getChildren().add(shovel);

        return scene;
    }

    private void gameover(boolean winStatus)
    {
        isPaused = true;
        (player.getAllLevels())[level.getNo()] = null;

        if (winStatus)
        {
            (player.getAvailableLevels())[Math.min(5,level.getNo()+1)] = true;

            if (1<=level_no && level_no<=3)
                plantUnlocked[level_no+1] = true;

            try { Player.serialize(player); }
            catch (IOException exc) { }
        }

        stage.setScene(myapp.getPlayer().getLevelMenuScene());
    }

    private void createPeaShooter (Plants plan, boolean flag, int row_no, int column_no)
    {
        Timeline peagen = new Timeline(new KeyFrame(Duration.millis(1500), new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event) {
                            if (isPaused || plan.getVBox().getChildren().isEmpty()) return;
                            VBox res = rows.get(row_no).addPea(column_no, flag, middle_point[column_no]+2);
                            if (res==null) return;
                            peasound.play();
                            root.getChildren().add(res);
                        }
                    }));
        peagen.setCycleCount(Timeline.INDEFINITE);
        peagen.play();
    }

    private void createSunFlowerAni (Plants plant, int row_no, int column_no)
    {
        Plants plan = plant;
        Timeline sunfgen = new Timeline(new KeyFrame(Duration.millis(6000), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (isPaused || plan.getVBox().getChildren().isEmpty()) return;
                            VBox psuned = produceSun(middle_point[column_no]-30,rows.get(row_no).getMiddle());
                        }
                    }));
        sunfgen.setCycleCount(Timeline.INDEFINITE);
        sunfgen.play();
    }

    private void handlePlants(Row row)
    {
        Map<Plants,Integer> plants = row.getPlants();

        for (Iterator<Map.Entry<Plants,Integer>> i = plants.entrySet().iterator(); i.hasNext(); )
        { 
            Map.Entry<Plants,Integer> entry = i.next(); 

            Plants plant = entry.getKey();

            if (plant.getHealth() <= 0 || row.isColumnOkay(entry.getValue()))
            {
                plant.getVBox().getChildren().clear();
                row.markEmpty(entry.getValue());
                i.remove();
            }
        }
    }

    private void handlePeas(Row row)
    {
        Set<Peas> peas = row.getPeas();

        for (Iterator<Peas> i = peas.iterator(); i.hasNext(); )
        {
            Peas pea = i.next();
            boolean stillThere = true;

            if (pea.getVBox().getTranslateX()>=1100)
            {
                pea.getVBox().getChildren().clear();
                i.remove();
                continue;
            }

            for (Zombies zombie : row.getZombies())
            {
                double diff = zombie.getVBox().getTranslateX() - pea.getVBox().getTranslateX();

                if (-35<=diff && diff<=5)
                {
                    zombie.decreaseHealth(pea.getAttack());
                    if (pea.isFreezing()) zombie.freeze();

                    pea.getVBox().getChildren().clear();
                    i.remove();

                    stillThere = false;
                    break;
                }
            }

            if (stillThere)
            {    
                double new_pos = pea.getVBox().getTranslateX() + pea.getXdelta();
                pea.getVBox().setTranslateX(new_pos);
                pea.xpos = new_pos;
            }
        }
    }

    private void handleLawnMowers(Row row)
    {
        LawnMower lawnmower = row.getLawnMower();

        for (Zombies zombie : row.getZombies())
        {
            if (lawnmower==null && zombie.getVBox().getTranslateX()<=HOUSE_LAST_LINE)
            {
                gameover(false);
                return;
            }

            if (lawnmower==null) continue;

            if (zombie.getVBox().getTranslateX()<=HOUSE_LAST_LINE)
                lawnmower.startMoving();

            if (Math.abs(zombie.getVBox().getTranslateX() - lawnmower.getVBox().getTranslateX()) < 10)
                zombie.decreaseHealth(10000);
        }

        if (lawnmower==null) return;

        if (lawnmower.moves())
        {
            double new_pos = lawnmower.getVBox().getTranslateX() + lawnmower.getXdelta();
            lawnmower.getVBox().setTranslateX(new_pos);
            lawnmower.xpos = new_pos;
        }

        if (lawnmower.getVBox().getTranslateX() >= RIGHTMOST_LINE)
        {
            lawnmower.getVBox().getChildren().clear();
            row.removeLawnMower();
        }
    }

    private void handleZombies(Row row)
    {
        Set<Zombies> zombies = row.getZombies();

        for (Iterator<Zombies> i = zombies.iterator(); i.hasNext(); )
        {
            Zombies zombie = i.next();
            boolean move_flag = true;

            if (zombie.getHealth() <= 0)
            {
                level.increaseKill();
                zombie.getVBox().getChildren().clear();
                i.remove();
                continue;
            }

            for (Map.Entry<Plants,Integer> plant_c : row.getPlants().entrySet())
            {
                Plants plant = plant_c.getKey();

                if (Math.abs(zombie.getVBox().getTranslateX() - plant.getVBox().getTranslateX()) < 10)
                {
                    plant.decreaseHealth(zombie.getAttack());
                    move_flag = false;
                    break;
                }
            }

            if (move_flag)
            {
                double new_pos = zombie.getVBox().getTranslateX() + zombie.getXdelta();
                zombie.getVBox().setTranslateX(new_pos);
                zombie.xpos = new_pos;
            }    
        }
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

        HBox scoreboard=new HBox();
        scoreboard.setSpacing(40);
        scoreboard.setMaxWidth(900.0);
        scoreboard.setMaxHeight(500.0);
        Image image = new Image("fsun.png");
        ImageView view_image= new ImageView(image);

        l_score=new Button(""+level.score);
        l_score.setFont(Font.font("Serif", FontWeight.EXTRA_BOLD, 30));
        l_score.setStyle("-fx-background-radius: 4em;" + "-fx-background-color: #99ffcc;");
        l_score.setMinWidth(125);
        l_score.setTranslateY(10);
        l_score.setTranslateX(-50);

        b_progress=new Button("Progress = "+level.getProgress()+"%");
        b_progress.setFont(Font.font("Serif", FontWeight.BOLD, 15));
        b_progress.setStyle("-fx-text-fill: #0000ff; -fx-background-color: #99ffcc;");
        b_progress.setMinWidth(200);
        b_progress.setTranslateY(20);
        b_progress.setTranslateX(-50);

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
        move_sun =new TranslateTransition();
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
                level.score+=25;    
                move_sun.stop();
                sun.getChildren().clear();
            }
        );

        return sun;
    }

    public VBox produceSun(int x,int y)
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
                level.score+=25;    
                sun.getChildren().clear();
            }
        );

        ArrayList<Boolean> done = new ArrayList<>(); done.add(false);
        Timeline remove_sun = new Timeline(new KeyFrame(Duration.millis(3000), new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (isPaused || done.get(0)) return;
                            done.set(0,true);
                            sun.getChildren().clear();
                        }}));
        remove_sun.setCycleCount(Timeline.INDEFINITE);
        remove_sun.play();

        allTimelines.put(remove_sun,done);

        sun.setTranslateX(x);
        sun.setTranslateY(y);
        root.getChildren().add(sun);
        return sun;
    }

    private VBox createPlantMenu()
    {
        VBox menu = new VBox(50);
        menu.setSpacing(0);
        menu.setMaxWidth(180);
        menu.setMaxHeight(70);

        String[] resources_paths = {"card_sunflower.png", "card_peashooter.png", "card_freezepeashooter.png",
                "card_wallnut.png", "card_cherrybomb.png"};

        for (String s: resources_paths)
        { 
            Image image = new Image(getClass().getResourceAsStream("blurred_"+s));
            ImageView imageView=new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(120);
            plantmenuimageviews.add(imageView);
            plantmenublurredimages.add(image);
            plantmenuimages.add(new Image(getClass().getResourceAsStream(s)));
            menu.getChildren().add(imageView);
        }

        return menu;
    }
}
