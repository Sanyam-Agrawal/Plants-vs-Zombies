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

public class PlantsMenu
{
    public static VBox createScene()
    {
        VBox menu = new VBox(50);
        menu.setSpacing(40);
        menu.setMaxWidth(250.0);
        menu.setMaxHeight(400.0);
        Image image = new Image("menu.png");
        ImageView view_image= new ImageView(image);
        // view_image.setFitHeight(70);
        // view_image.setFitWidth(70);

        menu.getChildren().add(view_image);

        Button go = new Button("");
        go.setStyle("-fx-background-radius: 4em; " +"-fx-min-width: 3px; " +"-fx-min-height: 3px; " +"-fx-max-width: 3px; " +"-fx-max-height: 3px;");
        go.setTranslateX(480);
        go.setTranslateY(180);

        Image image = new Image(getClass().getResourceAsStream("go.jpg"));
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(130);
        imageView.setFitWidth(140);

        return menu;
    }
}