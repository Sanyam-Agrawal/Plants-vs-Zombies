
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

import java.util.*;

public class Game extends Scenes
{
	private BorderPane root;

	private double mouseX, mouseY;

	private Pane[] centerRows = new Pane[5];
	Pane Lawn_Mower[] = new Pane[5];
	TranslateTransition move_mower[] = new TranslateTransition[5];
	int score=0,progress=0;

	public Game(App app,Stage st)
	{
		super(app,st);
	}

	@Override
	public Scene createScene()
	{
		root = new BorderPane();
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

		root.getChildren().add(this.displayScore());

		root.setLeft(gameVBox);

		root.getChildren().add(this.createLawnMower());

		Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>()
					{
						@Override
						public void handle(ActionEvent event) {
							root.getChildren().add(createSun());
						}
					}));
		fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
		fiveSecondsWonder.play();

		Scene scene=new Scene(root,1100,600);
		scene.getStylesheets().add(Game.class.getResource("game.css").toExternalForm());

		root.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mouseX = event.getSceneX();
				mouseY = event.getSceneY();
				System.out.println(mouseX + " + " + mouseY);
			}
		});

		VBox plant_chooser = createPlantMenu();
		root.getChildren().add(plant_chooser);

		move_mower[2].setCycleCount(1);
		move_mower[2].play();

		return scene;
	}

	public HBox displayScore()
	{
		Button redundant = new Button("whatever");
		redundant.setDisable(true);
		redundant.setVisible(false);

		Image image_s = new Image("shovel.png");
		ImageView view_image_s= new ImageView(image_s);
		view_image_s.setFitHeight(75);
		view_image_s.setFitWidth(75);

		HBox scoreboard=new HBox();
		scoreboard.setSpacing(40);
		scoreboard.setMaxWidth(900.0);
		scoreboard.setMaxHeight(500.0);
		Image image = new Image("sun.png");
		ImageView view_image= new ImageView(image);
	  
		this.score=125;
		Button l_score=new Button(Integer.toString(this.score));
		l_score.setFont(Font.font("Serif", FontWeight.EXTRA_BOLD, 30));
		l_score.setStyle("-fx-background-radius: 4em;" + "-fx-background-color: #99ffcc;");
		l_score.setMinWidth(125);
		l_score.setTranslateY(10);
		l_score.setTranslateX(-50);

		this.progress=54;
		Button b_progress=new Button("Progress = "+this.progress+"%");
		b_progress.setFont(Font.font("Serif", FontWeight.BOLD, 15));
		b_progress.setStyle("-fx-text-fill: #0000ff; -fx-background-color: #99ffcc;");
		b_progress.setMinWidth(200);
		b_progress.setTranslateY(20);
		b_progress.setTranslateX(-50);

		scoreboard.getChildren().add(view_image_s);
		scoreboard.getChildren().add(view_image);
		scoreboard.getChildren().add(l_score);
		scoreboard.getChildren().add(b_progress);
		scoreboard.setTranslateX(400);
		scoreboard.setTranslateY(-10);
		return scoreboard;
	}

	public VBox placeZombie()
	{
		VBox zombie = new VBox(50);
		zombie.setSpacing(40);
		zombie.setMaxWidth(250.0);
		zombie.setMaxHeight(100.0);
		Creature zom=new Zombies();
		Image image = zom.getImg();
		ImageView view_image= new ImageView(image);

		zombie.getChildren().add(view_image);
		TranslateTransition move_zombie =new TranslateTransition();
		move_zombie.setDuration(Duration.millis(5000)); 
		move_zombie.setNode(view_image); 
		move_zombie.setByX(-150);
		move_zombie.setCycleCount(1); 

		zombie.setTranslateX(300);
		zombie.setTranslateY(200);
		move_zombie.play();
		return zombie;
	}

	public VBox placePeashooter()
	{
		VBox v_plant = new VBox(50);
		v_plant.setSpacing(40);
		v_plant.setMaxWidth(250.0);
		v_plant.setMaxHeight(100.0);
		Creature plant=new PeaShooter();
		Image image = plant.getImg();
		ImageView view_image= new ImageView(image);

		v_plant.getChildren().add(view_image);
		v_plant.setTranslateX(280);
		v_plant.setTranslateY(90);
		return v_plant;
	}

	public VBox placePlant(Plants plant, double x, double y)
	{
		System.out.println("Placing at" + x + " " + y);
		VBox v_plant = new VBox(50);
		v_plant.setSpacing(40);
		v_plant.setMaxWidth(250.0);
		v_plant.setMaxHeight(100.0);
		Image image = plant.getImg();
		ImageView view_image = new ImageView(image);

		v_plant.getChildren().add(view_image);
		v_plant.setTranslateX(x);
		v_plant.setTranslateY(y);
		return v_plant;
	}

	public VBox createLawnMower()
	{
		VBox mower = new VBox(50);
		mower.setSpacing(40);
		mower.setMaxWidth(250.0);
		mower.setMaxHeight(400.0);
		for(int i=0;i<5;i++)
		{
			Image image = new Image("lawn_mower.gif");
			ImageView view_image= new ImageView(image);
			view_image.setFitHeight(70);
			view_image.setFitWidth(70);

			mower.getChildren().add(view_image);
			move_mower[i]=new TranslateTransition();
			move_mower[i].setDuration(Duration.millis(3000)); 
			move_mower[i].setNode(view_image); 
			move_mower[i].setByX(950); 
			move_mower[i].setCycleCount(1); 
			move_mower[i].setAutoReverse(false); 
		}
		mower.setTranslateX(170);
		mower.setTranslateY(60);
		return mower;
	}

	public VBox createSun()
	{
		VBox sun = new VBox(50);
		sun.setSpacing(40);
		sun.setMaxWidth(250.0);
		sun.setMaxHeight(400.0);
		Image image = new Image("sun.png");
		ImageView view_image= new ImageView(image);

		sun.getChildren().add(view_image);
		TranslateTransition move_sun =new TranslateTransition();
		move_sun.setDuration(Duration.millis(4000)); 
		move_sun.setNode(view_image); 

		move_sun.setByY(1100); 
		move_sun.setCycleCount(1); 
		move_sun.setAutoReverse(false); 

		sun.setTranslateX(200+250*Math.random());
		sun.setTranslateY(10);
		move_sun.play();
		return sun;
	}

	public VBox createPlantMenu()
	{
		VBox menu = new VBox(50);
		menu.setSpacing(0);
		menu.setMaxWidth(100);
		menu.setMaxHeight(75);

		String[] resources_paths = {"card_sunflower.png", "card_peashooter.png", "card_freezepeashooter.png",
									"card_wallnut.png", "card_cherrybomb.png"};

		// DataFormat[] dataformats_resources = { new DataFormat("sunflower"), new DataFormat("peashooter"),
		// 									   new DataFormat("freezepeashooter"),
		// 									   new DataFormat("wallnut"), new DataFormat("cherrybomb") };

		// int plantNo = -1;
		// int i = 0;
		for (String s: resources_paths)
		{ 
			Image image = new Image(getClass().getResourceAsStream(s));
			ImageView imageView=new ImageView(image);
			imageView.setFitHeight(100);
			imageView.setFitWidth(75);
			// DataFormat d = dataformats_resources[i];
			// imageView.setOnDragDetected(e -> {
			// 	Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
			// 	db.setDragView(imageView.snapshot(null, null)); 
			// 	ClipboardContent cc = new ClipboardContent();
			// 	cc.put(d, " ");
			// 	db.setContent(cc);
			// 	System.out.println("drag start");
			// 	});
			menu.getChildren().add(imageView);
			// ++i;
		}
		// root.setOnDragOver(e -> {
		// 	Dragboard db = e.getDragboard();
		// 	boolean flag = false;
		// 	for (DataFormat d : dataformats_resources) if (db.hasContent(d)) flag = true;
		// 	if (flag)
		// 		 e.acceptTransferModes(TransferMode.MOVE);
		// 	System.out.println("drag over");
		// });

		// root.setOnDragDropped(e -> {
		// 	System.out.println("drag drop start");
		// 	Dragboard db = e.getDragboard();
		// 	boolean flag = false;
		// 	for (DataFormat d : dataformats_resources) if (db.hasContent(d)) flag = true;
		// 	if (flag) {
		// 		// ((Pane)draggingB.get(0).getParent()).getChildren().remove(draggingB.get(0));
		// 		//add plant at that point
		// 		placePlant(new SunFlower(),mouseX,mouseY);
		// 		e.setDropCompleted(true);
		// 	}
		// 	if (plantNo != -1) System.out.println("drag drop end");
		// });

		return menu;
	}
}