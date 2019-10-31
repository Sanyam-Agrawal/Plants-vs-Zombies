
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.geometry.Pos;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

import javafx.scene.layout.GridPane;
public class Background{
    Stage stage;
    App myapp;
    public Background(App app,Stage st)
    {
        stage=st;
        myapp=app;
    }

    public Scene createScene(){
        GridPane grid=new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(125, 25, 25, 25));
        Button go = new Button("");
        go.setStyle(
            "-fx-background-radius: 4em; " +
            "-fx-min-width: 3px; " +
            "-fx-min-height: 3px; " +
            "-fx-max-width: 3px; " +
            "-fx-max-height: 3px;"
        );
        go.setTranslateX(480);
        go.setTranslateY(180);
        Image image = new Image(getClass().getResourceAsStream("go.jpg"));
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(130);
        imageView.setFitWidth(140);
        go.setGraphic(imageView);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
                public void handle(ActionEvent e) 
                { 
                    stage.setScene(myapp.getMainMenuScene());
                } 
            }; 

        go.setOnAction(event); 
        grid.getChildren().add(go);

        Scene scene=new Scene(grid,1100,600);
        scene.getStylesheets().add(Background.class.getResource("background.css").toExternalForm());
        return scene;
    }
    //private methods ==================================================================================================================================

    //main method ==================================================================================================================================
    public static void main(String[] args){

    }
}
