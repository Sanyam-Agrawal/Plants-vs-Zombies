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
    int score=0,progress=0;
    public Stage stage;
    public App myapp;
    private Player player;
    private LevelMenu level_menu;
    private Scene level_menu_scene;
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

        ///////////////////////////////////////////////////////////////////////////////////////

        ArrayList<Row> rows = new ArrayList<>();
        for (int i=0; i<5; ++i)
        {
            rows.add( new Row(95 + i*100) );
            VBox mower = rows.get(i).getLawnMower().getVBox();
            root.getChildren().add(mower);
            mower.setTranslateX(180);
            mower.setTranslateY(rows.get(i).getMiddle());
        }

        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent event) {
                            root.getChildren().add(createSun());
                        }
                    }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

        Scene scene=new Scene(root,1100,600);
        scene.getStylesheets().add(Game.class.getResource("game.css").toExternalForm());

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mouseX = event.getSceneX();
                    mouseY = event.getSceneY();
                    System.out.println(mouseX + " + " + mouseY);
                }
            });

        VBox plant_chooser = createPlantMenu();
        root.getChildren().add(plant_chooser);

        return scene;
    }

    public boolean[] getAvailabelLevels()
    {
        return player.getAvailabelLevels();
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

        this.score=125;
        Button l_score=new Button(Integer.toString(this.score));
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

    public VBox placeZombie()
    {
        VBox zombie = new VBox(50);
        zombie.setSpacing(40);
        zombie.setMaxWidth(250.0);
        zombie.setMaxHeight(100.0);
        Creature zom=new Zombies();
        Image image = zom.getImg();
        ImageView view_image= new ImageView(image);

        zombie.getChildren().add(view_image);
        TranslateTransition move_zombie =new TranslateTransition();
        move_zombie.setDuration(Duration.millis(5000)); 
        move_zombie.setNode(view_image); 
        move_zombie.setByX(-150);
        move_zombie.setCycleCount(1); 

        zombie.setTranslateX(300);
        zombie.setTranslateY(200);
        move_zombie.play();
        return zombie;
    }

    public VBox placePlant(Plants plant, double x, double y)
    {
        System.out.println("Placing at" + x + " " + y);
        VBox v_plant = new VBox(50);
        v_plant.setSpacing(40);
        v_plant.setMaxWidth(250.0);
        v_plant.setMaxHeight(100.0);
        Image image = plant.getImg();
        ImageView view_image = new ImageView(image);

        v_plant.getChildren().add(view_image);
        v_plant.setTranslateX(x);
        v_plant.setTranslateY(y);
        return v_plant;
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
        move_sun.setDuration(Duration.millis(4000)); 
        move_sun.setNode(view_image); 

        move_sun.setByY(1100); 
        move_sun.setCycleCount(1); 
        move_sun.setAutoReverse(false); 

        sun.setTranslateX(200+250*Math.random());
        sun.setTranslateY(10);
        move_sun.play();
        return sun;
    }

    public VBox createPlantMenu()
    {
        VBox menu = new VBox(50);
        menu.setSpacing(0);
        menu.setMaxWidth(100);
        menu.setMaxHeight(75);

        String[] resources_paths = {"card_sunflower.png", "card_peashooter.png", "card_freezepeashooter.png",
                "card_wallnut.png", "card_cherrybomb.png"};

        for (String s: resources_paths)
        { 
            Image image = new Image(getClass().getResourceAsStream(s));
            ImageView imageView=new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(75);
            menu.getChildren().add(imageView);
        }

        return menu;
    }
}