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
    public VBox createScene()
    {
        VBox menu = new VBox(50);
        menu.setSpacing(0);
        menu.setMaxWidth(100);
        menu.setMaxHeight(75);

        String[] resources_paths = {"card_sunflower.png", "card_peashooter.png", "card_freezepeashooter.png",
                                    "card_wallnut.png", "card_cherrybomb.png"};

        for (String s: resources_paths)
        { 
            Image image = new Image(getClass().getResourceAsStream(s));
            ImageView imageView=new ImageView(image);
            imageView.setFitHeight(100);
            imageView.setFitWidth(75);
            menu.getChildren().add(imageView);
        }

        return menu;
    }
}