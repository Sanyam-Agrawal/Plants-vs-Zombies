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
import javafx.animation.*; 
import javafx.util.Duration;

public abstract class Creature extends GridObject
{
    private String imgSrc;
    private Image img;

    private int health;

    public Creature(String imgSrc){
        super(10,0);
        this.imgSrc = imgSrc;
        this.img = new Image(this.imgSrc);
        this.vBox = this.createVBox();
    }

    protected VBox createVBox()
    {
        VBox created = new VBox(50);
        created.setSpacing(40);
        created.setMaxWidth(250.0);
        created.setMaxHeight(100.0);
        ImageView view_image= new ImageView(this.img);
        created.getChildren().add(view_image);
        return created;
    }

    public int getHealth() { return this.health; }
    public void decreaseHealth (int delta) { this.health -= delta; }

    public Image getImg(){
        return this.img;
    }

    public void setImgSrc(String newImgSrc){
        this.imgSrc = newImgSrc;
        this.img = new Image(this.imgSrc);
    }
}