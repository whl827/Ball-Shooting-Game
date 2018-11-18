import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainGame extends Application{

	//main variables
	private Scene scene;	
	protected Pane mainPane;
	private Text chance;	//update the counter on top left
	private int chanceCounter;
	private boolean isShot;	//main ball state of the game
	AnimationTimer shootingTimer;
	private MovingCircle shootingBall; //main ball
	private ImageView pointer; //line that points to shoot
	private Pane levelPane; //to clear obstacles in each level
	private boolean level1finished, level2finished, //level indicators
	level3finished, level4finished, level5finished;
	//level 1 
	private Entity attendance;
	private Target elementary;
	//level 2
	private Entity pencil;
	private Entity eraser;
	private Target middle;
	//level 3
	private Bar bar;
	private Entity act, sat;
	private Starbucks starbucks;
	private Target high;
	//level 4
	private Bar HBar, VBar1, VBar2;
	private Entity redBull; //removed professor image
	private Monster monster;
	private Target usc;
	//level 5
	private ArrayList<Internship> internships;
	private Target google;
	//Enemy
	Random rand = new Random();
	private MovingCircle enemy;
	private Pane targetPane;
	//button and characters
	private Pane buttonPane;
	private Image mainCharacterImage;
	private int characterIndex; //index for images
	private Button chooseCharacterButton, instructionButton,
				   startNewGameButton, quitButton;
	private Alert instruction; //instruction dialog
	//game controller/background
	GameController gc; GameBackground gb;
	
	//gamefinsiehd indicator
	private boolean gameFinished = false;

	@Override
	public void start(Stage stage) throws Exception {

		//set size of the window
		mainPane = new Pane();
		scene = new Scene(mainPane, 600, 600);
		stage.setScene(scene); stage.show();
		//set initial state of game
		isShot = false;
		level1finished = false; level2finished = false;
		level3finished = false; level4finished = false; level5finished = false;
		gc = new GameController();
		//set main background
		gb = new GameBackground(scene);
		gb.setMainGame(mainPane);
		//counter for the game
		chanceCounter = 10;
		chance = new Text("Chance: 10" );
		chance.setX(25); chance.setY(25);
		mainPane.getChildren().add(chance);
		//set pointer for shooting
		pointer = new ImageView("image/pointer.png");
		pointer.setFitWidth(50); pointer.setFitHeight(75);
		pointer.setX(scene.getWidth()/2-pointer.getFitWidth()/2);
		pointer.setY(scene.getHeight()-pointer.getFitHeight()/2);	
		mainPane.getChildren().add(pointer);
		//game part 3, choosing image and buttons for other options
		mainCharacterImage = null;
		targetPane = new Pane(); //for enenmy pane
		//set up buttons and instruction dialog
		setUpButtons(); setUpInstruction();
		//choosing different character based on the button
		characterIndex = 0; //index for different images
		//chose a new character
		chooseCharacterButton.setOnMouseClicked(e->{
			if(!isShot){
				characterIndex = (characterIndex+1)%4;
				switch(characterIndex){
				case 0: mainCharacterImage = null;
				chooseCharacterButton.setText("\tDefault\t\t"); 
				break; 
				case 1: mainCharacterImage = new Image("image/soccerball.png");
				chooseCharacterButton.setText("\tSoccer\t\t");
				break;
				case 2: mainCharacterImage = new Image("image/basketball.png");
				chooseCharacterButton.setText("\tBasketball\t");
				break;
				case 3: mainCharacterImage = new Image("image/football.png");
				chooseCharacterButton.setText("\tFootball\t\t");
				}
			}
		});
		//when instruction button is clicked, show instruction dialog
		instructionButton.setOnMouseClicked(e->{
			instruction.showAndWait();
		});
		//quit game
		quitButton.setOnMouseClicked(e->{
			mainPane.getChildren().clear();
			System.exit(0);
		});
		//new game
		startNewGameButton.setOnAction(e->{
			if(!isShot || gameFinished){
				restart();
			}
		});
		//star the game
		gc.startGame();
		//user control for the game
		scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){	
				if(!isShot && !gameFinished){
					//move to right
					if(e.getCode()==KeyCode.D){
						if(pointer.getX()<scene.getWidth()*2/3){			
							pointer.setX(pointer.getX()+5);
						}
					}
					//move to left
					else if(e.getCode()==KeyCode.A){
						if(pointer.getX()>scene.getWidth()/5){
							pointer.setX(pointer.getX()-5);
						}
					}
					//change angle to right
					else if(e.getCode()==KeyCode.RIGHT){
						if(pointer.getRotate()<60){
							pointer.setRotate(pointer.getRotate()+20);
						}
					}
					//change angle to left
					else if(e.getCode()==KeyCode.LEFT){
						if(pointer.getRotate()>-60){
							pointer.setRotate(pointer.getRotate()-20);
						}
					}
					//shoot the ball
					else if(!isShot && e.getCode()==KeyCode.SPACE){
						isShot = true; //indicate shot
						//create a new shooting ball and add
						shootingBall = new MovingCircle(pointer.getX()+pointer.getFitWidth()/2, 
								scene.getHeight()-10.01, 10);
						//if player chooses image, put it in.
						if(mainCharacterImage!=null){
							shootingBall.setFill(new ImagePattern(mainCharacterImage));
						}
						mainPane.getChildren().add(shootingBall);
						//determine the angle for shooting
						switch((int)pointer.getRotate()){
						case -60: shootingBall.setXSpeed(-3.75); shootingBall.setYSpeed(-3); break;
						case -40: shootingBall.setXSpeed(-2.75); shootingBall.setYSpeed(-4); break;
						case -20: shootingBall.setXSpeed(-1.25); shootingBall.setYSpeed(-4.25); break;
						case 0: shootingBall.setXSpeed(0); shootingBall.setYSpeed(-4); break;
						case 20: shootingBall.setXSpeed(1.25); shootingBall.setYSpeed(-4.25); break;
						case 40: shootingBall.setXSpeed(2.75); shootingBall.setYSpeed(-4); break;
						case 60: shootingBall.setXSpeed(3.75); shootingBall.setYSpeed(-3); break;
						}
						//determine which level to start
						if(level1finished==false) gc.level1Animation();
						else if(level2finished==false) gc.level2Animation();
						else if(level3finished==false) gc.level3Animation();
						else if(level4finished==false) gc.level4Animation();
						else if(level5finished==false) gc.level5Animation();
					}
				}
			}
		});
	}
	class GameController{
		//start the game
		public void startGame(){
			setUpLevel1();
		}
		//set obstacles, target, main character for each level
		public void setUpLevel1(){
			//set up target
			levelPane = new Pane();
			elementary = new Target(100, 100, 300, 50); elementary.setImage("image/elementary.png");
			//set up defense
			attendance = new Entity(200,50, 250,300); attendance.setImage("image/attendance.png");
			//add to main
			levelPane.getChildren().addAll(elementary, attendance);
			mainPane.getChildren().add(levelPane);
		}
		public void level1Animation() {
			shootingTimer = new AnimationTimer(){
				@Override
				public void handle(long now) {
					generateEnemy();
					checkBounds(elementary, 1); //check if game is over and check bounds of scene
					collisionDetection(attendance); //update when ball hits obstacles
					shootingBall.update();
				}
			};
			shootingTimer.start();
		}
		//set up level 2 objects
		public void setUpLevel2(){
			//set up target
			middle = new Target(100, 50, 50, 50); middle.setImage("image/middle.png");
			//set up defense
			pencil = new Entity(200,100, 300,300); pencil.setImage("image/pencil.png");
			eraser = new Entity(150,75, 100,400); eraser.setImage("image/eraser.png");
			//set up main player
			levelPane = new Pane(); levelPane.getChildren().addAll(pencil, eraser, middle);
			mainPane.getChildren().add(levelPane);
		}
		//create level 2 functionalities 
		public void level2Animation() {
			shootingTimer = new AnimationTimer(){
				@Override
				public void handle(long now) {	
					//same as previous level
					generateEnemy();
					checkBounds(middle,2);
					collisionDetection(pencil); collisionDetection(eraser);
					shootingBall.update();
				}
			};
			shootingTimer.start();
		}
		//set up level 3 objects
		public void setUpLevel3(){
			//set up target
			high = new Target(50, 50, 300, 50); high.setImage("image/high.png");
			//set up defense
			act = new Entity(100,50,400,160); act.setImage("image/act.png");
			sat = new Entity(100,50,75,150); sat.setImage("image/sat.png");
			starbucks = new Starbucks(25,25,140,350); starbucks.setImage("image/starbucks.png");
			bar = new Bar(150,450,150,10,Color.DARKBLUE);
			//set up main player
			levelPane = new Pane(); levelPane.getChildren().addAll(act, sat, starbucks, high, bar);
			mainPane.getChildren().add(levelPane);
			starbucks.move(2000); starbucks.setCircleLocation(300,200); starbucks.setCircleSize(100);
			bar.moveLeftAndRight(1250, scene);
		}
		//create level 3 functionalities 
		public void level3Animation() {
			shootingTimer = new AnimationTimer(){
				@Override
				public void handle(long now) {		
					generateEnemy();
					checkBounds(high,3);
					collisionDetection(act); collisionDetection(sat);
					collisionDetection2(starbucks); collisionDetection(bar);
					shootingBall.update();
				}
			};
			shootingTimer.start();
		}
		//set up level 4 objects
		public void setUpLevel4(){
			//set up target
			usc = new Target(75, 75, 300, 200); usc.setImage("image/usc.png");
			//set up defense
			monster = new Monster(100,50,450,390); monster.setImage("image/monster.png");
			redBull = new Entity(100,50,100,400); redBull.setImage("image/redbull.gif");
			//professor = new Entity(25,25,325,150); professor.setImage("image/professor.png");
			HBar = new Bar(250,300,150,10,Color.DARKBLUE);
			VBar1 = new Bar(250,100,10,100,Color.DARKBLUE);
			VBar2 = new Bar(400,100,10,100,Color.DARKBLUE);
			//set up main player
			levelPane = new Pane(); levelPane.getChildren().addAll(usc, monster, redBull, HBar, VBar1, VBar2); //removed professor 
			mainPane.getChildren().add(levelPane);
			VBar1.moveUpAndDown(2000, scene); VBar2.moveUpAndDown(1500, scene);
			monster.rotateAround(new Circle(monster.getX()+monster.getFitWidth()/2, monster.getY()+monster.getFitHeight()/2,1));
		}
		//create level 4 functionalities 
		public void level4Animation() {
			shootingTimer = new AnimationTimer(){
				@Override
				public void handle(long now) {		
					generateEnemy();
					checkBounds(usc,4);
					collisionDetection(monster); collisionDetection(redBull);
					//collisionDetection(professor); 
					collisionDetection(HBar);collisionDetection(VBar1);collisionDetection(VBar2);
					shootingBall.update();
				}
			};
			shootingTimer.start();
		}
		//set up level 5 objects
		public void setUpLevel5(){
			//set up target
			google = new Target(50, 25, 50, 50); google.setImage("image/google.png");
			//set up main player
			levelPane = new Pane();
			levelPane.getChildren().addAll(google);
			internships = new ArrayList<>();
			for(int i=0; i<12; i++){
				Internship e = new Internship(150,50);
				if(i<4){
					e.setImage("image/internship.png"); e.setX(10); e.setY(100*(i+1));
				}else if(i<8){
					e.setImage("image/experience.png"); e.setX(220); e.setY((120*(i-3)));
				}else{
					e.setImage("image/internship.png"); e.setX(390); e.setY((80*(i-7)));
				}
				internships.add(e); //add to the array list
				e.fadeInAndOut(); // randomly fade in and out
				levelPane.getChildren().add(e);
			}
			mainPane.getChildren().add(levelPane);
		}
		//create level 5 functionalities 
		public void level5Animation() {
			shootingTimer = new AnimationTimer(){
				@Override
				public void handle(long now) {	
					generateEnemy();
					checkBounds(google, 5);
					for(int i=0; i<internships.size(); i++){
						collisionDetection(internships.get(i));
					}
					shootingBall.update();
				}
			};
			shootingTimer.start();
		}
		//reset game when you fail
		public void resetLevel(){
			//reset shooting and timer
			isShot = false; shootingTimer.stop(); 
			//delete shooting ball
			Pane p = new Pane(); p.getChildren().add(shootingBall); p.getChildren().clear();
			//reset chance counter
			chanceCounter = 10; chance.setText("Chances: " + String.valueOf(chanceCounter));
			//reset location of pointer
			pointer.setRotate(0); pointer.setX(scene.getWidth()/2-pointer.getFitWidth()/2); 
			pointer.setY(scene.getHeight()-pointer.getFitHeight()/2);
			//reset direction of the ball
			shootingBall.setXSpeed(0); shootingBall.setYSpeed(-4);
			shootingBall.setCenterX(pointer.getX()+pointer.getFitWidth()/2);
			//clear enemy
			enemy = null;
			if(targetPane!=null){
				targetPane.getChildren().clear();
			}
		}
		//do what is necessary when each level is finished
		public void levelFinished(int level){
			resetLevel();
			levelPane.getChildren().clear(); levelPane=new Pane();
			switch(level){
			case 1: level1finished = true; setUpLevel2(); break;
			case 2: level2finished = true; setUpLevel3(); break;
			case 3: level3finished = true; setUpLevel4(); break;
			case 4: level4finished = true; setUpLevel5(); break;
			case 5: level5finished = true; gameFinished(); break;
			}
		}
		//update when ball detects collision
		public void collisionDetection(Entity obstacle){
			//when ball detects an obstacle
			if(shootingBall.intersects(obstacle.getBoundsInParent())){

				if(shootingBall.getBoundsInParent().getMaxX()<=obstacle.getBoundsInParent().getMinX()+1){
					shootingBall.changeDirectionX();
				} //when ball hits right side of obstacle
				else if(shootingBall.getBoundsInParent().getMinX()>=obstacle.getBoundsInParent().getMaxX()-1){
					shootingBall.changeDirectionX();
				} 
				//when ball hits left corner
				else if(shootingBall.getBoundsInParent().getMaxX()<=obstacle.getBoundsInParent().getMinX()+10){
					shootingBall.changeDirectionX(); shootingBall.changeDirectionY();
				} //when ball hits the right corner
				else if(shootingBall.getBoundsInParent().getMinX()>=obstacle.getBoundsInParent().getMaxX()-10){
					shootingBall.changeDirectionX();	shootingBall.changeDirectionY();
				} //when ball hits top or bottom of obstacle
				else{
					shootingBall.changeDirectionY();
				}
				//decrease the counter
				chanceCounter--; chance.setText("Chances: " + String.valueOf(chanceCounter));
			}
		}
		public void collisionDetection(Bar obstacle){
			//when ball detects an obstacle
			if(shootingBall.intersects(obstacle.getBoundsInParent())){
				//when ball detects an obstacle
				if(shootingBall.intersects(obstacle.getBoundsInParent())){
					if(shootingBall.getBoundsInParent().getMaxX()<=obstacle.getBoundsInParent().getMinX()+1){
						shootingBall.changeDirectionX();	
					} //when ball hits right side of obstacle
					else if(shootingBall.getBoundsInParent().getMinX()>=obstacle.getBoundsInParent().getMaxX()-1){
						shootingBall.changeDirectionX();
					} 
					//when ball hits left corner
					else if(shootingBall.getBoundsInParent().getMaxX()<=obstacle.getBoundsInParent().getMinX()+10){
						shootingBall.changeDirectionX(); shootingBall.changeDirectionY();
					} //when ball hits the right corner
					else if(shootingBall.getBoundsInParent().getMinX()>=obstacle.getBoundsInParent().getMaxX()-10){
						shootingBall.changeDirectionX();	shootingBall.changeDirectionY();
					} //when ball hits top or bottom of obstacle
					else{
						shootingBall.changeDirectionY();
					}
					//decrease the counter
					chanceCounter--; chance.setText("Chances: " + String.valueOf(chanceCounter));
				}
			}
		} //when collide, dies immediately
		public void collisionDetection2(Entity obstacle){
			if(shootingBall.intersects(obstacle.getBoundsInParent())){
				chanceCounter=0;
				chance.setText("Chances: " + String.valueOf(chanceCounter));
			}
		} //enemy colliding with the shooting ball
		public void collisionDetectionEnemy(MovingCircle enemy){
			if(shootingBall.intersects(enemy.getBoundsInParent())){
				chanceCounter = 0;
				chance.setText("Chances: " + String.valueOf(chanceCounter));
				if(targetPane!=null){
					targetPane.getChildren().clear();
				}
			}
		}
		//randomly generate enemy
		public void generateEnemy(){
			//randomly generate enemy
			int x = rand.nextInt(200);
			if(enemy == null && x==100){
				enemy = new MovingCircle(rand.nextInt(200),0,30);
				enemy.setXSpeed(rand.nextInt(4)+1); enemy.setYSpeed(rand.nextInt(4)+1);
				int enemyNum = rand.nextInt(3);
				switch(enemyNum){
				case 0: enemy.setFill(new ImagePattern(new Image("image/enemy.png"))); break;
				case 1: enemy.setFill(new ImagePattern(new Image("image/enemy2.png"))); break;
				case 2: enemy.setFill(new ImagePattern(new Image("image/enemy3.png"))); break;
				}
				targetPane = new Pane();
				targetPane.getChildren().add(enemy);
				mainPane.getChildren().add(targetPane);
			}
			if(enemy!=null){
				enemy.update();
				collisionDetectionEnemy(enemy);
			}
		}
		//checking collision and if level is finished
		public void checkBounds(Target target, int level){
			double left = shootingBall.getCenterX() - 10; double right = shootingBall.getCenterX() + 10;
			double top = shootingBall.getCenterY() - 10; double bottom = shootingBall.getCenterY() + 10;
			//if game chance is over
			if(chanceCounter<=0){
				resetLevel();
			}//if you hit target (go to next level)
			else if(shootingBall.intersects(target.getBoundsInParent())){
				levelFinished(level);
			} //when it his the scene or obstacles
			else{		
				//update counter and change direction when hit scene
				if ( bottom >= scene.getHeight() || top <= 0 ) {
					chanceCounter--; chance.setText("Chances: " + String.valueOf(chanceCounter));
					shootingBall.changeDirectionY();
				}
				else if ( right >= scene.getWidth() || left <= 0 ) {
					chanceCounter--; chance.setText("Chances: " + String.valueOf(chanceCounter));
					shootingBall.changeDirectionX();
				}
			}
		}
	}
	//set up buttons
	public void setUpButtons(){
		//initialize
		chooseCharacterButton = new Button("\tCharacter\t\t");
		instructionButton = new Button("Instruction");
		startNewGameButton = new Button("New Game");
		quitButton = new Button("Quit Game");
		//equal width
		chooseCharacterButton.setMaxWidth(Double.MAX_VALUE);
		instructionButton.setMaxWidth(Double.MAX_VALUE);
		startNewGameButton.setMaxWidth(Double.MAX_VALUE);
		quitButton.setMaxWidth(Double.MAX_VALUE);
		//add to scene
		buttonPane = new Pane();
		VBox vbox = new VBox(chooseCharacterButton, instructionButton, startNewGameButton, quitButton);
		vbox.setPadding(new Insets(0, 20, 10, 20)); 
		buttonPane.getChildren().addAll(vbox);
		buttonPane.setLayoutX(scene.getWidth()-145);
		buttonPane.setLayoutY(scene.getHeight()-105);
		mainPane.getChildren().add(buttonPane);
		//lose focus
		chooseCharacterButton.setFocusTraversable(false);
		instructionButton.setFocusTraversable(false);
		startNewGameButton.setFocusTraversable(false);
		quitButton.setFocusTraversable(false);
	}
	//set alert for instruction
	public void setUpInstruction(){
		instruction = new Alert(AlertType.INFORMATION);
		instruction.setTitle("Instruction");
		instruction.setHeaderText("How To Play Game");
		instruction.setContentText("\n\nMain Objectives:\n\nShoot the target for each level. Try to avoid a random enemy or moving entities\n\n"
				+"Control:\n\nSpaceBar: \t\t\tShoot\nKey A & D: \t\t\tMove Left / Right\nRight / Left Arrow Key: \tChange Angle");
	}
	//when you clear for restart
	public void restart(){
		//game restarts
		gameFinished = false;
		//reset clear button
		chooseCharacterButton.setText("\tCharacter\t\t");
		characterIndex = 0;
		mainCharacterImage = null;
		//reset chance
		chanceCounter = 10;
		chance.setText("Chance: 10" );
		//reset levels and shot
		isShot = false;
		level1finished = false; level2finished = false;
		level3finished = false; level4finished = false; level5finished = false;
		//game background
		gb.resetBackground(scene);
		levelPane.getChildren().clear();
		shootingBall = null;
		//reset poitner location
		pointer.setX(scene.getWidth()/2-pointer.getFitWidth()/2);
		pointer.setY(scene.getHeight()-pointer.getFitHeight()/2);
		pointer.setRotate(0);
		gc.startGame();
	}
	//when game is finished
	public void gameFinished(){
		gameFinished = true;
		chance.setText("Congratulation, You Won!\nSelect New Game to restart or Quit Game to quit");
	}
	public static void main(String[] args) {
		Application.launch(args);
	}
}