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
		super(10,0);
		this.isIced = ice;
	}

	public boolean isFreezing() { return this.isIced; }

	protected VBox createVBox()
	{
		//TODO
		return null;
	}

	public int getAttack() { return 10; }
}