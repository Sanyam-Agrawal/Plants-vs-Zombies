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

public class Background extends Scenes
{
    public Background(App app,Stage st)
    {
        super(app,st);
    }

    @Override
    public Scene createScene(){
        GridPane grid=new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(125, 25, 25, 25));

        Button go = new Button("");
        go.setStyle("-fx-background-radius: 4em; " +"-fx-min-width: 3px; " +"-fx-min-height: 3px; " +"-fx-max-width: 3px; " +"-fx-max-height: 3px;");
        go.setTranslateX(480);
        go.setTranslateY(180);

        Image image = new Image(getClass().getResourceAsStream("go.jpg"));
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(130);
        imageView.setFitWidth(140);

        go.setGraphic(imageView);
        EventHandler<ActionEvent> go_event = new EventHandler<ActionEvent>()
            { 
                public void handle(ActionEvent e) 
                { 
                    stage.setScene(myapp.getMainMenuScene());
                } 
            }; 
        go.setOnAction(go_event); 
        grid.getChildren().add(go);

        Scene scene=new Scene(grid,1100,600);
        scene.getStylesheets().add(Background.class.getResource("background.css").toExternalForm());
        return scene;
    }
}