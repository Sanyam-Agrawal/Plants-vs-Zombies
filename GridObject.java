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

class GridObject implements Serializable
{
	private double xdelta, ydelta;

	private VBox vBox;

	GridObject (VBox _vBox, double i1, double i2)
	{
		vBox = _vBox;
		xdelta = i1;
		ydelta = i2;
	}

	public VBox getVBox() { return this.vBox; }

	public double getXdelta() { return this.xdelta; }
	public double getYdelta() { return this.ydelta; }
}