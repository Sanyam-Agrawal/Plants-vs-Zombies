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
import javafx.beans.value.*;

public class LevelMenu
{
    public Stage stage;
    public App myapp;
    Button level_buttons[];

    public LevelMenu(App app,Stage stage)
    {
        this.myapp=app;
        this.stage=stage;
    }

    public Scene createScene(){
        BorderPane root = new BorderPane();
        VBox menuVBox = new VBox(10);

        boolean available_levels[]=myapp.getPlayer().getAvailabelLevels();
        level_buttons=new Button[6];
        menuVBox.setAlignment(Pos.CENTER);
        for(int i=1;i<6;i++)
        {
            level_buttons[i] = new Button("Level "+i);
            level_buttons[i].setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
            level_buttons[i].setStyle("-fx-background-color: #BCEABE");
            if(available_levels[i])
            {
                level_buttons[i].setStyle("-fx-background-color: #32CD32");
                int no = i;
                EventHandler<ActionEvent> level_button_event = new EventHandler<ActionEvent>() 
                    { 
                        public void handle(ActionEvent e) 
                        { 
                            myapp.click();
                            stage.setScene(myapp.createGameScene(stage,no));
                        } 
                    };
                level_buttons[i].setOnAction(level_button_event);
            }
            menuVBox.getChildren().add(level_buttons[i]);
        }

        Button go_back = new Button("Go to Main Menu");
        go_back.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        go_back.setStyle("-fx-background-color: #32CD32");
        EventHandler<ActionEvent> go_back_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                { 
                    myapp.click();
                    stage.setScene(myapp.getMainMenuScene());
                } 
            };

        go_back.setOnAction(go_back_event);
        menuVBox.getChildren().add(go_back);

        menuVBox.setTranslateY(20);
        root.setCenter(menuVBox);

        Scene scene=new Scene(root,1100,600);
        scene.getStylesheets().add(MainMenu.class.getResource("name_menu.css").toExternalForm());
        return scene;
    }
}
