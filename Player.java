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

class Player
{
    private boolean available_levels[];
    private String name;
    private Level all_levels[];

    transient private LevelMenu level_menu;
    transient private Scene level_menu_scene;

    public Player(String n,App myapp,Stage stage)
    {
        name=n;
        available_levels=new boolean[6];
        available_levels[1]=true;
        all_levels=new Level[6];
        this.level_menu=new LevelMenu(myapp,stage);
    }

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

}