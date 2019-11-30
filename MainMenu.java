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

public class MainMenu 
{
    public Stage stage;
    public App myapp;
    
    public MainMenu(App app,Stage stage)
    {
        this.myapp=app;
        this.stage=stage;
    }
    
    public Scene createScene(){

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
                    myapp.click();
                    stage.setScene(myapp.getNameMenuScene(true));
                } 
            };
        start_game.setOnAction(start_game_event);
        menuVBox.getChildren().add(start_game);

        Button load_game = new Button("Load Game");
        load_game.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        load_game.setStyle("-fx-background-color: #32CD32");
        load_game.setTranslateX(8);
        EventHandler<ActionEvent> load_game_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                { 
                    myapp.click();
                    stage.setScene(myapp.getNameMenuScene(false));
                } 
            }; 
            
        load_game.setOnAction(load_game_event); 
        menuVBox.getChildren().add(load_game);

        Button exit = new Button("Exit");
        exit.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        exit.setStyle("-fx-background-color: #32CD32");
        exit.setTranslateX(70);
        EventHandler<ActionEvent> exit_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                { 
                    myapp.click();
                    Platform.exit();
                } 
            }; 
        exit.setOnAction(exit_event); 
        menuVBox.getChildren().add(exit);

        menuVBox.setTranslateX(430);
        menuVBox.setTranslateY(100);
        root.setCenter(menuVBox);

        Scene scene=new Scene(root,1100,600);
        scene.getStylesheets().add(MainMenu.class.getResource("main_menu.css").toExternalForm());
        return scene;
    }
}
