import javafx.event.EventHandler;
import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle
{
	private Game game;
	private int row;
	private int col;
  	private double width;
	private double height;

	private Plants occupyingPlant = null;
	private boolean plantIsOccupying = false;

	public Tile(Game game, int row, int col){
		super(100.0, 100.0);
		this.game = game;
		this.width = 100.0;
		this.height = 100.0;
		this.row = row;
		this.col = col;
	}

	public Tile(Game game, int row, int col, Color color){
		super(100.0, 100.0, color);
		this.game = game;
		this.width = 100.0;
		this.height = 100.0;
		this.row = row;
	  	this.col = col;
	}

	public Tile(Game game, int xCoord, int row, int col){
	        
		super(xCoord, 0.0, 100.0, 100.0);
		this.game = game;
		this.width = 100.0;
		this.height = 100.0;
		this.row = row;
	  	this.col = col;
	}

	public Tile(Game game, int xCoord, int row, int col, Color color){
		super(xCoord, 1.0, 78.0, 110-row%2*3);
		this.setFill(color);
		this.game = game;
		this.width = 60.0;
		this.height = 100.0;
		this.row = row;
	  	this.col = col;
	}

	public Tile(Game game, int row, int col, Color color, double width, double height){
		super(width, height, color);
		this.game = game;
		this.row = row;
		this.col = col;
		this.width = width;
		this.height = height;
	}

	// public Game getGame(){return this.game;}
	// public int getRow(){return this.row;}
	// public int getCol(){return this.col;}
	public Plants getOccupyingPlant(){return this.occupyingPlant;}
	// public boolean getStudentIsOccupying(){return this.studentIsOccupying;}
	// //public ArrayList<Human> getAllHumansOnTile(){return this.allHumansOnTile;}
	// public boolean getExpelHammerIsSelected(){return this.expelHammerIsSelected;}

	// public void setRow(int newRow){this.row = newRow;}
	// public void setCol(int newCol){this.col = newCol;}
	// public void setOccupyingStudent(Student newOccupyingStudent){this.occupyingStudent = newOccupyingStudent;}
	// public void setStudentIsOccupying(boolean newStudentIsOccupying){this.studentIsOccupying = newStudentIsOccupying;}
	// public void setExpelHammerIsSelected(boolean newExpelHammerIsSelected){this.expelHammerIsSelected = newExpelHammerIsSelected;}

	public void setImage(String url){
		this.setFill(new ImagePattern(new Image(url)));
	}

	public void setUpLeftTile(){
		Tile thisTile = this;
		this.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (thisTile.getStroke().equals(Color.BLACK)){
					thisTile.setStroke(Color.RED);
				} else {
					thisTile.setStroke(Color.BLACK);
				}
			}
		});
	}

	public void setUpCenterTile(){
		this.setOpacity(0.90);
		// this.setStrokeWidth(2.0);
		// this.setStroke(Color.BLACK);

		Tile thisTile = this;
		this.setOnMouseEntered(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (!thisTile.plantIsOccupying){
					thisTile.setFill(Color.GREEN);
				} else {
					// if (thisTile.getOccupyingPlant().getImgSrc().equals("/students/studyingstudent1.png")){
						// StudyingStudent studyingStudent = (StudyingStudent)thisTile.getOccupyingStudent();
						// thisTile.getGame().setBrainPower(thisTile.getGame().getBrainPower() + studyingStudent.getAttackPower());
						// studyingStudent.collectedBrainPower();
					// }
				}
			}
		});

		this.setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent mouseEvent) {
				if (!thisTile.plantIsOccupying){
					thisTile.setFill(Color.TRANSPARENT);
				}
			}
		});

		this.setOnMousePressed(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent mouseEvent) {
				// if (thisTile.studentIsOccupying && thisTile.expelHammerIsSelected){
					// thisTile.getGame().getInstantiatedStudents().remove(thisTile.getOccupyingStudent());
					// thisTile.setOccupyingStudent(null);
					// thisTile.setStudentIsOccupying(false);
					// thisTile.setFill(Color.LIGHTGRAY);
				// }
			}
		});
	}
}
