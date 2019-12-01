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
import java.io.*;

public class GameMenu 
{
    public Stage stage;
    public App myapp;
    
    public GameMenu(App app,Stage stage)
    {
        this.myapp=app;
        this.stage=stage;
    }
    
    public Scene createScene()
    {
        BorderPane root=new BorderPane();
        VBox menuVBox = new VBox(10.0); 

        Button resume_game = new Button("Resume Game");
        resume_game.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        resume_game.setStyle("-fx-background-color: #32CD32");
        resume_game.setTranslateX(-20);
        EventHandler<ActionEvent> resume_game_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                { 
                    myapp.click();
                    stage.setScene(myapp.getGameScene());
                    myapp.getGame().isPaused = false;
                } 
            }; 
        resume_game.setOnAction(resume_game_event);
        menuVBox.getChildren().add(resume_game);

        Button save_game = new Button("Save Game");
        save_game.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        save_game.setStyle("-fx-background-color: #32CD32");
        save_game.setTranslateX(8);
        EventHandler<ActionEvent> save_game_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                {
                    myapp.click();
                    try { Player.serialize(myapp.getPlayer()); }
                    catch (IOException exc) {
                        Alert a = new Alert(AlertType.INFORMATION); 
                        a.setContentText("Some Input/Output error occurred!!\n" +
                            "Unfortunately, we are unable to save your file.\n" +
                            "Please share the console output with the authors.");
                        a.show();

                        System.out.println("-------START COPYING HERE-------");
                        exc.printStackTrace();
                        System.out.println("-------STOP COPYING HERE-------");

                        return;
                    }
                    stage.setScene(myapp.getMainMenuScene());
                } 
            }; 
        save_game.setOnAction(save_game_event); 
        menuVBox.getChildren().add(save_game);

        Button restart_game = new Button("Restart Level");
        restart_game.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        restart_game.setStyle("-fx-background-color: #32CD32");
        restart_game.setTranslateX(-5);
        EventHandler<ActionEvent> restart_game_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                { 
                    myapp.click();
                    (myapp.getPlayer().getAllLevels())[myapp.getGame().getLevelNo()] = null;
                    stage.setScene(myapp.createGameScene(stage,myapp.getGame().getLevelNo()));
                } 
            }; 
        restart_game.setOnAction(restart_game_event); 
        menuVBox.getChildren().add(restart_game);

        Button exit = new Button("Exit");
        exit.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        exit.setStyle("-fx-background-color: #32CD32");
        exit.setTranslateX(70);
        EventHandler<ActionEvent> exit_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                { 
                    myapp.click();
                    stage.setScene(myapp.getMainMenuScene());
                } 
            }; 
        exit.setOnAction(exit_event); 
        menuVBox.getChildren().add(exit);

        menuVBox.setTranslateX(430);
        menuVBox.setTranslateY(50);
        root.setCenter(menuVBox);

        Scene scene=new Scene(root,1100,600);
        scene.getStylesheets().add(MainMenu.class.getResource("game_menu.css").toExternalForm());

        return scene;
    }
}