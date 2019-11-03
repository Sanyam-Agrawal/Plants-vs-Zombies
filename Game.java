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

public class Game extends Scenes
{
    private Tile[][] centerTiles = new Tile[5][9];
    private Pane[] centerRows = new Pane[5];
    public Game(App app,Stage st)
    {
        super(app,st);
    }

    @Override
    public Scene createScene(){

        BorderPane root = new BorderPane();
        VBox gameVBox = new VBox(10.0);

        Button game_menu = new Button("MENU");
        game_menu.setFont(Font.font("Serif", FontWeight.BOLD, 24));
        game_menu.setStyle("-fx-background-color: #1E001E; -fx-text-fill: #FFFFFF");
        game_menu.setMaxWidth(120);
        game_menu.setTranslateX(980);
        EventHandler<ActionEvent> game_menu_event = new EventHandler<ActionEvent>() 
            { 
                public void handle(ActionEvent e) 
                { 
                    stage.setScene(myapp.getGameMenuScene());
                } 
            };
        game_menu.setOnAction(game_menu_event);
        gameVBox.getChildren().add(game_menu);

        root.setLeft(gameVBox);
        root.setCenter(this.createCenterTiles());

        Scene scene=new Scene(root,1100,600);
        scene.getStylesheets().add(Game.class.getResource("game.css").toExternalForm());
        return scene;
    }
    public VBox createCenterTiles(){
		VBox centerTiles = new VBox(50);
		centerTiles.setSpacing(55);
		centerTiles.setMaxWidth(950.0);
		centerTiles.setMaxHeight(475.0);
			int centerTilesRowCount = 0;
			int centerTilesColCount = 0;
 			for (int r=0; r<5; r++){
				Pane row = new Pane();
				int startingXCoord = 140;
				for (int c=0; c<9; c++){
					Tile tile = new Tile(this, startingXCoord, r, c, Color.TRANSPARENT);
					this.centerTiles[r][c] = tile;
					tile.setUpCenterTile();
					row.getChildren().add(tile);
					startingXCoord += 81;
				}
				this.centerRows[r] = row;
				centerTiles.getChildren().add(row);
    		}
		return centerTiles;
	}
    public static void main(String[] args){

    }
}