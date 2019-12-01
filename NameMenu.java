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
import java.io.*;

public class NameMenu
{
    public Stage stage;
    public App myapp;
    public boolean is_new=true;
    public NameMenu(App app,Stage stage)
    {
        this.myapp=app;
        this.stage=stage;
    }

    public Scene createScene(boolean is_new){
        BorderPane root = new BorderPane();
        VBox menuVBox = new VBox(50);
        this.is_new=is_new;

        TextField name = new TextField()
            {
                @Override public void replaceText(int start, int end, String text) {
                    super.replaceText(start, end, text.toUpperCase());
                }
            };
        name.setPromptText("ENTER YOUR USERNAME");
        name.setStyle("-fx-background-color: #F9F4F3;-fx-text-fill: blue;-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);-fx-font: 30px Tahoma;");
        name.setMaxWidth(500);
        name.setAlignment(Pos.CENTER);
        name.setTranslateX(-150);
        name.setTranslateY(120);
        menuVBox.getChildren().add(name);

        Button start_game = new Button("Start Game");
        start_game.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        start_game.setStyle("-fx-background-color: #32CD32");
        start_game.setTranslateX(415);
        start_game.setTranslateY(380);
        EventHandler<ActionEvent> start_game_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                { 
                    myapp.click();
                    if(name.getText().length()!=0 && name.getText().matches("^[A-Z]{1,15}$"))
                    {
                        if(is_new)
                        {
                            myapp.createPlayer(name.getText(),stage);
                        }
                        else
                        {
                            Player player;

                            try { player = Player.deserialize(name.getText()); }
                            catch (FileNotFoundException exc)
                            {
                                Alert a = new Alert(AlertType.INFORMATION); 
                                a.setContentText("No saved file for this name exists.\nPlease try another name.");
                                a.show();
                                return;
                            }
                            catch (IOException exc)
                            {
                                Alert a = new Alert(AlertType.INFORMATION); 
                                a.setContentText("Some Input/Output error occurred!!\n" +
                                    "Unfortunately, we are unable to load your file.\n" +
                                    "Please share the console output with the authors.");
                                a.show();

                                System.out.println("-------START COPYING HERE-------");
                                exc.printStackTrace();
                                System.out.println("-------STOP COPYING HERE-------");

                                return;
                            }
                            catch (ClassNotFoundException exc)
                            {
                                Alert a = new Alert(AlertType.INFORMATION); 
                                a.setContentText("Problem. Please try again later.");
                                a.show();
                                return;
                            }

                            if (player==null)
                            {
                                Alert a = new Alert(AlertType.INFORMATION); 
                                a.setContentText("Problem. Please try again later.");
                                a.show();
                                return;
                            }

                            player.setAttr(myapp,stage);
                            myapp.setPlayer(player);
                        }

                        stage.setScene(myapp.getPlayer().getLevelMenuScene());
                    }
                    else
                    {
                        Alert a = new Alert(AlertType.INFORMATION); 
                        a.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                        a.setContentText("Name must consist of letters and its length must be between 1 and 15.");
                        a.show();
                    }
                }
            };

        start_game.setOnAction(start_game_event);
        menuVBox.getChildren().add(start_game);

        Button go_back = new Button("Go Back");
        go_back.setFont(Font.font("Brush Script MT", FontWeight.NORMAL, 36));
        go_back.setStyle("-fx-background-color: #32CD32");
        go_back.setMaxWidth(200);
        go_back.setTranslateX(-440);
        go_back.setTranslateY(260);
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

        menuVBox.setTranslateX(430);
        menuVBox.setTranslateY(50);
        root.setCenter(menuVBox);

        Scene scene=new Scene(root,1100,600);
        scene.getStylesheets().add(MainMenu.class.getResource("name_menu.css").toExternalForm());
        return scene;
    }
}
