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

class LawnMower extends GridObject
{
	private boolean isMoving;

	LawnMower ()
	{
		super(10,0);
		this.vBox = this.createVBox();
		isMoving = false;
	}

	public void startMoving() { isMoving = true; }
	public boolean moves() { return isMoving; }

	protected VBox createVBox()
	{
		VBox mower = new VBox(50);
		Image image = new Image("lawn_mower.gif");
		ImageView view_image= new ImageView(image);
		view_image.setFitHeight(70);
		view_image.setFitWidth(70);
		mower.getChildren().add(view_image);
		mower.setTranslateX(180);
		return mower;
	}
}