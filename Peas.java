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

class Peas extends GridObject
{
	private Row row;
    private boolean isIced;

    Peas(Row row, boolean ice)
    {
        super(2,0);
        this.row = row;
        this.isIced = ice;
        this.vBox = this.createVBox();
    }

    public boolean isFreezing() { return this.isIced; }

    protected VBox createVBox()
    {
        Image image = isIced ? new Image(getClass().getResourceAsStream("bluepea.png")) : new Image(getClass().getResourceAsStream("pea.png"));
        VBox vbox=new VBox(50);
        ImageView imageView=new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        vbox.getChildren().add(imageView);
        return vbox;
    }

    public boolean shouldShoot() { return !row.getZombies().isEmpty(); }

    public int getAttack() { return 10; }
}