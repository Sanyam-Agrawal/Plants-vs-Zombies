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
	private boolean isIced;

	Peas(boolean ice)
	{
		super(1,0);
		this.isIced = ice;
	}

	public boolean isFreezing() { return this.isIced; }

	protected VBox createVBox()
	{
		if(isIced)
		{
			VBox vbox=new VBox();
			Image image = new Image(getClass().getResourceAsStream("pea.png"));
            ImageView imageView=new ImageView(image);
            imageView.setFitHeight(100);
            // imageView.setFitWidth(75);
            imageView.setFitWidth(120);
            vbox.getChildren().add(imageView);
            return vbox;
		}
		else
		{
			VBox vbox=new VBox();
			Image image = new Image(getClass().getResourceAsStream("pea.png"));
            ImageView imageView=new ImageView(image);
            imageView.setFitHeight(100);
            // imageView.setFitWidth(75);
            imageView.setFitWidth(120);
            vbox.getChildren().add(imageView);
            return vbox;
		}
	}

	public int getAttack() { return 10; }
}