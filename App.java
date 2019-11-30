import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
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
    private Player player;
    private GameMenu game_menu;
    private NameMenu name_menu;
    Media sound = new Media(new File("buttonclick.mp3").toURI().toString());
    private Scene background_scene, main_menu_scene, game_menu_scene,name_menu_scene,game_scene;
    private Game game;
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
        this.game_menu_scene=game_menu.createScene();
    }

    public void click()
    {
        (new MediaPlayer(sound)).play();
    }

    public Game getGame()
    {
        return game;   
    }

    public Scene getMainMenuScene()
    {
        return main_menu_scene;
    }

    public Scene createGameScene(Stage stage,int level)
    {
        game=new Game(this,stage,player);
        this.game_scene = this.game.createScene(player,level);
        return game_scene;
    }

    public Scene getGameScene()
    {
        return game_scene;   
    }

    public Scene getNameMenuScene(boolean is_new)
    {
        return this.name_menu_scene=name_menu.createScene(is_new);
    }

    public Scene getBackgroundScene()
    {
        return background_scene;
    }

    public Player createPlayer(String name,Stage stage)
    {
        player=new Player(name,this,stage);
        return player;
    }

    public Player getPlayer()
    {
        return player;    
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