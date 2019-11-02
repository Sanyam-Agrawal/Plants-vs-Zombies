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
import javafx.scene.control.Alert.*;
import javafx.application.*;

public class MainMenu extends Scenes 
{
    public MainMenu(App app,Stage stage)
    {
        super(app,stage);
    }

    @Override
    public   Scene createScene(){

        BorderPane root = new BorderPane();
        VBox menuVBox = new VBox(10.0);

        Button start_game = new Button("Start Game");
        start_game.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        start_game.setStyle("-fx-background-color: #32CD32");
        start_game.setTranslateX(5);
        EventHandler<ActionEvent> start_game_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                { 

                } 
            }; 
        Alert a = new Alert(AlertType.INFORMATION); 
        a.setContentText("This feature is not yet implemented!!"); 
        start_game.setOnAction(start_game_event);
        menuVBox.getChildren().add(start_game);

        Button choose_level = new Button("Choose Level");
        choose_level.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        choose_level.setStyle("-fx-background-color: #32CD32");
        choose_level.setTranslateX(-5);
        EventHandler<ActionEvent> choose_level_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                { 
                    a.show(); 
                } 
            }; 
        choose_level.setOnAction(choose_level_event); 
        menuVBox.getChildren().add(choose_level);

        Button load_game = new Button("Load Game");
        load_game.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        load_game.setStyle("-fx-background-color: #32CD32");
        EventHandler<ActionEvent> load_game_event = new EventHandler<ActionEvent>() 
            { 
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
        EventHandler<ActionEvent> exit_event = new EventHandler<ActionEvent>() 
            { 
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
        scene.getStylesheets().add(MainMenu.class.getResource("main_menu.css").toExternalForm());
        return scene;
    }

    public static void main(String[] args){

    }
}
