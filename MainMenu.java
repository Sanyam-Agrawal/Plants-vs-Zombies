
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.control.*; 
import javafx.scene.control.Alert.AlertType; 
import javafx.application.Platform;
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
        Button start_game = new Button("Start Game");
        start_game.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        start_game.setStyle("-fx-background-color: #32CD32");
        EventHandler<ActionEvent> start_game_event = new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 

                } 
            }; 

        Alert a = new Alert(AlertType.INFORMATION,"FYI"); 
        a.setContentText("This feature is not yet implemented!!"); 
        start_game.setTranslateX(5);
        start_game.setId("start_game_button");
        start_game.setOnAction(start_game_event); 
        menuVBox.getChildren().add(start_game);

        Button choose_level = new Button("Choose Level");
        choose_level.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        choose_level.setStyle("-fx-background-color: #32CD32");
        EventHandler<ActionEvent> choose_level_event = new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 

                    // show the dialog 
                    a.show(); 
                } 
            }; 

        choose_level.setId("choose_level_button");
        choose_level.setTranslateX(-5);
        choose_level.setOnAction(choose_level_event); 
        menuVBox.getChildren().add(choose_level);

        Button load_game = new Button("Load Game");
        load_game.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        load_game.setStyle("-fx-background-color: #32CD32");
        EventHandler<ActionEvent> load_game_event = new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                    a.show();
                } 
            }; 

        load_game.setOnAction(load_game_event); 
        menuVBox.getChildren().add(load_game);

        Button exit = new Button("Exit");
        exit.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        exit.setStyle("-fx-background-color: #32CD32");
        exit.setTranslateX(50);
        EventHandler<ActionEvent> exit_event = new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                    Platform.exit();
                } 
            }; 

        exit.setOnAction(exit_event); 
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
