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
public class NameMenu
{
    public Stage stage;
    public App myapp;

    public NameMenu(App app,Stage stage)
    {
        this.myapp=app;
        this.stage=stage;
    }

    public Scene createScene(){
        BorderPane root = new BorderPane();
        VBox menuVBox = new VBox(50);

        TextField name = new TextField()

            {
                @Override public void replaceText(int start, int end, String text) {
                    super.replaceText(start, end, text.toUpperCase());
                }
            };
        name.setStyle("-fx-background-color: #F9F4F3");
        name.setStyle("-fx-text-fill: blue;-fx-font: 30px Tahoma;");
        name.setMaxWidth(200);
        menuVBox.getChildren().add(name);

        Button start_game = new Button("Start Game");
        start_game.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        start_game.setStyle("-fx-background-color: #32CD32");
        EventHandler<ActionEvent> start_game_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                { 
                    if(name.getText().length()!=0)
                    {
                        myapp.createGame(name.getText(),stage);
                        stage.setScene(myapp.getGame().getLevelMenuScene());
                    }
                } 
            };

        start_game.setOnAction(start_game_event);
        menuVBox.getChildren().add(start_game);

        Button go_back = new Button("Go Back");
        go_back.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        go_back.setStyle("-fx-background-color: #32CD32");
        go_back.setMaxWidth(200);
        EventHandler<ActionEvent> go_back_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                { 
                    stage.setScene(myapp.getMainMenuScene());
                } 
            };

        go_back.setOnAction(go_back_event);
        menuVBox.getChildren().add(go_back);

        menuVBox.setTranslateX(430);
        menuVBox.setTranslateY(50);
        root.setCenter(menuVBox);

        Scene scene=new Scene(root,1100,600);
        scene.getStylesheets().add(MainMenu.class.getResource("name_menu.css").toExternalForm());
        return scene;
    }
}
