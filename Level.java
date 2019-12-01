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
	private int remain;
	private int no_of_incoming;
	private long frequency;
	private int max_severity;
	public int score;
	private int kill_count;
	private int spawnCount;
	private int[] remaining;

	private ArrayList<Row> rows;
	private long times[];

	Level (int _no)
	{
		no = _no;

		remain = 1;

		switch(no)
		{
			case 2:
				no_of_incoming = 20;
				frequency = 9000;
				max_severity = 2;
				remain = 5;
				break;

			case 3:
				no_of_incoming = 30;
				frequency = 8000;
				max_severity = 3;
				remain = 8;
				break;

			case 4:
				no_of_incoming = 40;
				frequency = 7000;
				max_severity = 4;
				remain = 13;
				break;

			case 5:
				no_of_incoming = 75;
				frequency = 3000;
				max_severity = 4;
				remain = 20;
				break;

			default:
				no_of_incoming = 5;
				frequency = 10000;
				max_severity = 1;
				remain = 1;
		}

		score = 100;
		kill_count = 0;
		spawnCount = 0;

		rows = new ArrayList<>();
        for (int i=0; i<5; ++i)
        {
            rows.add( new Row(95 + i*100) );
            VBox mower;
            while ((mower = rows.get(i).getLawnMower().getVBox()) == null);
            mower.setTranslateX(180);
            rows.get(i).getLawnMower().xpos = 180;
            mower.setTranslateY(rows.get(i).getMiddle());
            rows.get(i).getLawnMower().ypos = rows.get(i).getMiddle();
        }

        times = new long[5];
        for (int i=0; i<5; ++i) times[i] = -1;

        remaining = new int[5];
    	for (int i=0; i<5; ++i) remaining[i] = remain;
	}

	public void increaseKill() { ++kill_count; }

	public int getProgress() { return Math.min(99,Math.max(0,(int)(((double)kill_count)*100/no_of_incoming -1))); }

	public boolean spawn()
	{
		if (spawnCount==no_of_incoming) return false;
		++spawnCount;
		return true;
	}

	public boolean readyForFinalWave() { return kill_count==no_of_incoming; }

	public boolean waveRemaining(int no) { return !(--remaining[no] < 0); }

	public boolean waveFinished()
	{
		return (kill_count == ( no_of_incoming + 5*remain ) );
	}

	public int getNo() { return no; }
	public int getNo_of_incoming() { return no_of_incoming; }
	public long getFrequency() { return frequency; }
	public int getSeverity() { return max_severity; }

	public ArrayList<Row> getRows() { return rows; }

	public long[] getTimes()
	{
		if (times[0] == -1)
		{	
			long start_time = System.nanoTime();
	        for (int i=0; i<5; ++i) times[i] = start_time;
	    }
	    return times;    	
	}
}