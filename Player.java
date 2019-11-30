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
    transient private Game mygame;
    transient public Stage stage;
    transient public App myapp;

    transient private LevelMenu level_menu;
    transient private Scene level_menu_scene,game_scene;

    public Player(String n,App myapp,Stage stage)
    {
        name=n;
        this.stage=stage;
        this.myapp=myapp;
        available_levels=new boolean[6];
        available_levels[1]=true;
        this.level_menu=new LevelMenu(myapp,stage);
    }

    public Scene getLevelMenuScene()
    {
        return this.level_menu_scene=level_menu.createScene();
    }

    public Scene getGameScene()
    {
        if(mygame==null)
        {
            mygame=new Game(myapp,stage);
        }
        if(this.game_scene==null)
            this.game_scene = this.mygame.createScene();
        return game_scene;
    }
    public Game getGame()
    {
        return this.mygame;
    }
    public boolean[] getAvailabelLevels()
    {
        return available_levels;
    }

}