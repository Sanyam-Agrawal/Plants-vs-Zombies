
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
import javafx.scene.control.Alert.*;
import javafx.application.*;

public class App extends Application
{
    private MainMenu main_menu;
    private Background background;
    private Game game;
    private GameMenu game_menu;
    private NameMenu name_menu;
    
    private Scene background_scene, main_menu_scene, game_scene, game_menu_scene,name_menu_scene;
    
    public App()
    {
        
    }

    public App(Stage stage)
    {
        this.main_menu=new MainMenu(this,stage);
        this.background=new Background(this,stage);
        this.game_menu=new GameMenu(this,stage);
        this.name_menu=new NameMenu(this,stage);

        this.main_menu_scene=main_menu.createScene();
        this.background_scene=background.createScene();
        this.name_menu_scene=name_menu.createScene();
    }

    public Scene getMainMenuScene()
    {
        return main_menu_scene;
    }
    
    public Scene getNameMenuScene()
    {
        return name_menu_scene;
    }

    public Scene getBackgroundScene()
    {
        return background_scene;
    }
    public Scene getGameScene()
    {
        if(game_scene==null)
        this.game_scene = this.game.createScene();
        return game_scene;
    }

    public Game getGame()
    {
        return this.game;
    }
    public void createGame(String name,Stage stage)
    {
        if(this.game==null)
        {
            this.game=new Game(this,stage,name);
            // this.game_scene = this.game.createScene();
        }
    }

    public Scene getGameMenuScene()
    {
        
        return game_menu_scene;
    }

    @Override
    public void start(Stage stage) throws InterruptedException
    {
        stage.setTitle("Plants vs Zombies");
        App myapp=new App(stage);
        stage.setResizable(false);
        stage.setScene(myapp.background_scene);
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}