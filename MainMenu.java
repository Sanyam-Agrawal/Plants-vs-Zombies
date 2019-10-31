
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Pos;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class MainMenu implements SceneCreator {
    Stage stage;
    public MainMenu()
    {

    }

    @Override
    public   Scene createScene(){
        BorderPane root = new BorderPane();
        VBox menuVBox = new VBox(10.0);
        //START START START START START START START START START START START START START START START START START
        Button new_game = new Button("New Game");
        new_game.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        new_game.setStyle("-fx-background-color: #32CD32");
        EventHandler<ActionEvent> new_game_event = new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 

                } 
            }; 

        new_game.setTranslateX(5);
        new_game.setId("new_game_button");
        new_game.setOnAction(new_game_event); 
        menuVBox.getChildren().add(new_game);

        Button load_game = new Button("Load Game");
        load_game.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        load_game.setStyle("-fx-background-color: #32CD32");
        EventHandler<ActionEvent> load_game_event = new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 

                } 
            }; 

        new_game.setOnAction(load_game_event); 
        menuVBox.getChildren().add(load_game);

        Button exit = new Button("Exit");
        exit.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        exit.setStyle("-fx-background-color: #32CD32");
        exit.setTranslateX(50);
        EventHandler<ActionEvent> exit_event = new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                    System.exit(0);
                } 
            }; 

        new_game.setOnAction(exit_event); 
        menuVBox.getChildren().add(exit);

        menuVBox.setTranslateX(430);
        menuVBox.setTranslateY(50);
        root.setCenter(menuVBox);

        Scene scene=new Scene(root,1100,600);
        scene.getStylesheets().add
        (MainMenu.class.getResource("main_menu.css").toExternalForm());
        return scene;
    }
    //private methods ==================================================================================================================================

    //main method ==================================================================================================================================
    public static void main(String[] args){

    }
}
