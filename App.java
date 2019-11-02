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
    private  Scenes main_menu,background;
    
    private  Scene background_scene,main_menu_scene;
    public App()
    {
        
    }
    public App(Stage stage)
    {
        this.main_menu=new MainMenu(this,stage);
        this.background=new Background(this,stage);

        this.main_menu_scene=main_menu.createScene();
        this.background_scene=background.createScene();
    }

    public Scene getMainMenuScene()
    {
        return main_menu_scene;
    }

    public Scene getBackgroundScene()
    {
        return background_scene;
    }

    @Override
    public void start(Stage stage)throws InterruptedException
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