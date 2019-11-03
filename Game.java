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
public class Game extends Scenes
{
    private Tile[][] centerTiles = new Tile[5][9];
    private Pane[] centerRows = new Pane[5];
    Pane Lawn_Mower[] = new Pane[5];
    TranslateTransition move_mower[] = new TranslateTransition[5];

    public Game(App app,Stage st)
    {
        super(app,st);
    }

    @Override
    public Scene createScene(){

        BorderPane root = new BorderPane();
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

        root.setLeft(gameVBox);
        root.setCenter(this.createCenterTiles());

        root.getChildren().add(this.createLawnMower());
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

    @Override
    public void handle(ActionEvent event) {
        root.getChildren().add(createSun());
    }
}));
fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
fiveSecondsWonder.play();

        Scene scene=new Scene(root,1100,600);
        scene.getStylesheets().add(Game.class.getResource("game.css").toExternalForm());

        move_mower[2].setCycleCount(1); 
        move_mower[2].play(); 
        return scene;
    }

    public VBox createCenterTiles(){
        VBox centerTiles = new VBox(50);
        centerTiles.setSpacing(55);
        centerTiles.setMaxWidth(950.0);
        centerTiles.setMaxHeight(475.0);
        int centerTilesRowCount = 0;
        int centerTilesColCount = 0;
        for (int r=0; r<5; r++){
            Pane row = new Pane();
            int startingXCoord = 140;
            for (int c=0; c<9; c++){
                Tile tile = new Tile(this, startingXCoord, r, c, Color.TRANSPARENT);
                this.centerTiles[r][c] = tile;
                tile.setUpCenterTile();
                row.getChildren().add(tile);
                startingXCoord += 81;
            }
            this.centerRows[r] = row;
            centerTiles.getChildren().add(row);
        }
        return centerTiles;
    }

    public VBox createLawnMower()
    {
        VBox mower = new VBox(50);
        mower.setSpacing(40);
        mower.setMaxWidth(250.0);
        mower.setMaxHeight(400.0);
        for(int i=0;i<5;i++)
        {
            Image image = new Image("lawn_mower.gif");
            ImageView view_image= new ImageView(image);
            view_image.setFitHeight(70);
            view_image.setFitWidth(70);

            mower.getChildren().add(view_image);
            move_mower[i]=new TranslateTransition();
            move_mower[i].setDuration(Duration.millis(3000)); 
            //Setting the node for the transition 
            move_mower[i].setNode(view_image); 

            //Setting the value of the transition along the x axis. 
            move_mower[i].setByX(950); 

            //Setting the cycle count for the transition 
            move_mower[i].setCycleCount(1); 

            //Setting auto reverse value to false 
            move_mower[i].setAutoReverse(false); 

            //Playing the animation 
        }
        mower.setTranslateX(170);
        mower.setTranslateY(60);
        return mower;
    }

    public VBox createSun()
    {
        VBox sun = new VBox(50);
        sun.setSpacing(40);
        sun.setMaxWidth(250.0);
        sun.setMaxHeight(400.0);
        Image image = new Image("sun.png");
        ImageView view_image= new ImageView(image);
        // view_image.setFitHeight(70);
        // view_image.setFitWidth(70);

        sun.getChildren().add(view_image);
        TranslateTransition move_sun =new TranslateTransition();
        move_sun.setDuration(Duration.millis(4000)); 
        //Setting the node for the transition 
        move_sun.setNode(view_image); 

        //Setting the value of the transition along the x axis. 
        move_sun.setByY(1100); 

        //Setting the cycle count for the transition 
        move_sun.setCycleCount(1); 

        //Setting auto reverse value to false 
        move_sun.setAutoReverse(false); 

        //Playing the animation 
        sun.setTranslateX(200+250*Math.random());
        sun.setTranslateY(10);
        move_sun.play();
        return sun;
    }

    public static void main(String[] args){

    }
}