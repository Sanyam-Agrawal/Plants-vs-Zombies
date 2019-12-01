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

class Player implements Serializable
{
    private boolean available_levels[];
    private String name;
    private Level all_levels[];

    transient private LevelMenu level_menu;
    transient private Scene level_menu_scene;

    private boolean plant_unlocked[];

    public Player(String n,App myapp,Stage stage)
    {
        name=n;
        available_levels=new boolean[6];
        available_levels[1]=true;
        all_levels=new Level[6];
        plant_unlocked=new boolean[5];
        plant_unlocked[0]=true;
        plant_unlocked[1]=true;
        this.level_menu=new LevelMenu(myapp,stage);
    }

    public void setAttr(App myapp,Stage stage) { this.level_menu=new LevelMenu(myapp,stage); }

    public static void serialize(Player player) throws IOException
    {
        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream (new FileOutputStream (player.getName()+".savgm"));
            out.writeObject (player);
        }
        finally
        {
            out.close();
        }
    }

    public static Player deserialize (String filename) throws IOException, ClassNotFoundException
    {
        ObjectInputStream in = null;
        Player obj = null;

        try
        {
            in = new ObjectInputStream (new FileInputStream (filename+".savgm"));
            obj = (Player) in.readObject();

            File file = new File (filename+".savgm");
        }
        finally
        {
            if (in!=null) in.close();
            return obj;
        }
    }

    public String getName() { return name; }

    public Scene getLevelMenuScene()
    {
        return this.level_menu_scene=level_menu.createScene();
    }

    public Level getLevel(int level_no)
    {
        if (all_levels[level_no]==null) all_levels[level_no]=new Level(level_no);
        return all_levels[level_no];
    }

    public boolean[] getAvailableLevels()
    {
        return available_levels;
    }

    public Level[] getAllLevels()
    {
        return all_levels;
    }

    public boolean[] getPlantUnlocked()
    {
        return plant_unlocked;
    }
}