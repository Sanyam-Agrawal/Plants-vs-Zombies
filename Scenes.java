/**
 * This interface is used in all of the Scenes in the game.
 * This interface guarantees one method, createScene(), which creates the scene for any object that implements SceneCreator
 */
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

public abstract class Scenes{
    
    public Stage stage;
    public App myapp;

    public Scenes(App app,Stage st)
    {
        stage=st;
        myapp=app;
    }

    public abstract Scene createScene();
}
