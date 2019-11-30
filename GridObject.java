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

import java.io.*;

abstract class GridObject implements Serializable
{
	protected double xdelta, ydelta;

	protected VBox vBox;

	GridObject (double i1, double i2)
	{
		xdelta = i1;
		ydelta = i2;
	}

	protected abstract VBox createVBox();

	public VBox getVBox() { return this.vBox; }
	public void setVBox (VBox _VBox) { this.vBox = _VBox; }

	public double getXdelta() { return this.xdelta; }
	public double getYdelta() { return this.ydelta; }
}