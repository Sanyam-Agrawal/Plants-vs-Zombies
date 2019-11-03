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

public class Game extends Scenes
{
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

        

        root.setCenter(gameVBox);

        Scene scene=new Scene(root,1100,600);
        scene.getStylesheets().add(Game.class.getResource("game.css").toExternalForm());
        return scene;
    }

    public static void main(String[] args){

    }
}