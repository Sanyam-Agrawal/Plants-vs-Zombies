/**
 * This interface is used in all of the Scenes in the game.
 * This interface guarantees one method, createScene(), which creates the scene for any object that implements SceneCreator
 */

import javafx.scene.Scene;

public interface SceneCreator{
	public Scene createScene();
}
