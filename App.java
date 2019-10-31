
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javafx.application.Application;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.*;
import java.util.TimerTask;
import java.io.*;
import java.lang.*;

public class App extends Application
{
    private  MainMenu main_menu=null;
    private Scene main_menu_scene=null;
    private Background background=null;
    private  Scene background_scene=null;
    public App()
    {
    }

    public App(Stage stage)
    {
        this.main_menu=new MainMenu();
        this.background=new Background(this,stage);
        this.main_menu_scene=(main_menu).createScene();
        this.background_scene=background.createScene();
    }

    public Scene getMainMenuScene()
    {
        return main_menu_scene;
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