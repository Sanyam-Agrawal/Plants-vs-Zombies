/**
 * This interface is used in all of the Scenes in the game.
 * This interface guarantees one method, createScene(), which creates the scene for any object that implements SceneCreator
 */

import javafx.scene.Scene;

public interface SceneCreator{
	/**
	 * This creates a Scene object depending on
	 * how the body of this method is defined.
	 *
	 * @param Nothing.
	 * @return Scene A Scene object that is defined in implementing classes.
	*/
	public Scene createScene();
}
