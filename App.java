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
    private Scenes main_menu, background, game, game_menu;
    
    private Scene background_scene, main_menu_scene, game_scene, game_menu_scene;
    
    public App()
    {
        
    }

    public App(Stage stage)
    {
        this.main_menu=new MainMenu(this,stage);
        this.background=new Background(this,stage);
        this.game=new Game(this,stage);
        this.game_menu=new GameMenu(this,stage);

        this.main_menu_scene=main_menu.createScene();
        this.background_scene=background.createScene();
        this.game_menu_scene=game_menu.createScene();
    }

    public Scene getMainMenuScene()
    {
        return main_menu_scene;
    }

    public Scene getBackgroundScene()
    {
        return background_scene;
    }

    public Scene getGameScene()
    {
        if(game_scene==null) game_scene = this.game.createScene();
        return game_scene;
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