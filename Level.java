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
import java.io.*;

class Level implements Serializable
{
	private int no;
	private long frequency;
	private int max_severity;
	public int score;
	private int kill_count;

	Level (int _no)
	{
		no = _no;

		switch(no)
		{
			case 2:
				frequency = 9000;
				max_severity = 2;
				break;

			case 3:
				frequency = 8000;
				max_severity = 3;
				break;

			case 4:
				frequency = 7000;
				max_severity = 4;
				break;

			case 5:
				frequency = 3000;
				max_severity = 4;
				break;

			default:
				frequency = 10000;
				max_severity = 1;
		}

		score = 250;
		kill_count = 0;
	}

	public void increaseKill() { ++kill_count; }

	public long getFrequency() { return frequency; }
	public int getSeverity() { return max_severity; }
}