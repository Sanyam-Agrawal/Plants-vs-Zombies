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

import java.util.*;

class Row
{
	private int middle_point;

	private final Map<Plants,Integer> plants;
	private final Set<Peas> peas;
	private LawnMower lawnmower;
	private final Set<Zombies> zombies;

	private final boolean[] columns;

	Row (int _middle_point)
	{
		middle_point = _middle_point;
		plants = new HashMap<>();
		peas = new HashSet<>();
		lawnmower = new LawnMower();
		zombies = new HashSet<>();
		columns = new boolean[9];
	}

	public boolean isColumnOkay(int column) { return !columns[column]; }

	public void markEmpty(int column) { columns[column] = false; }

	public VBox addPlant(Plants plant, int column)
	{
		columns[column] = true;
		this.plants.put(plant,column);
		VBox result = plant.getVBox();
		result.setTranslateY(middle_point);
		return result;
	}

	public void removeLawnMower() { lawnmower = null; }

	public int getMiddle() { return this.middle_point; }
	public Map<Plants,Integer> getPlants() { return this.plants; }
	public Set<Peas> getPeas() { return this.peas; }
	public LawnMower getLawnMower() { return this.lawnmower; }
	public Set<Zombies> getZombies() { return this.zombies; }
}