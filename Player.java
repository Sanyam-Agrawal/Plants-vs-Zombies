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
    boolean available_levels[];
    String name;

    transient private LevelMenu level_menu;
    transient private Scene level_menu_scene;

    public Player(String n,App myapp,Stage stage)
    {
        name=n;
        available_levels=new boolean[6];
        available_levels[1]=true;
        this.level_menu=new LevelMenu(myapp,stage);
    }

    public Scene getLevelMenuScene()
    {
        return this.level_menu_scene=level_menu.createScene();
    }

    
    public boolean[] getAvailabelLevels()
    {
        return available_levels;
    }

}