import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;

public class GameBackground {

	private MediaView mediaView;
	private Sky sky;
	private Sun sun;
	private BackgroundMusic bgm;
	private Cloud cloud1,cloud2,cloud3;
	
	public GameBackground(Scene s){
		//background music
		bgm = new BackgroundMusic();
		bgm.playMusic();
		mediaView = new MediaView(bgm.getMusic());
		//background
		sky = new Sky(s.getWidth(), s.getHeight());	
		//create sun
		sun = new Sun(100,100,s.getWidth(),s.getHeight()/50);
		sun.moveSun(s, 0.02);
		//create clouds
		cloud1 = new Cloud(150,100,700,25);
		cloud2 = new Cloud(150,100,1000,100);
		cloud3 = new Cloud(150,100,1300,50);
		//move the clouds
		cloud1.moveClouds(s, 0.5);
		cloud2.moveClouds(s, 0.5);
		cloud3.moveClouds(s, 0.5);
	}
	
	public void setMainGame(Pane pane){
		pane.getChildren().addAll(sky,cloud1,cloud2, cloud3, mediaView, sun);
	}
	
	public void resetBackground(Scene s){
		//reset cloud position
		if(cloud1!= null){
			cloud1.setX(700); cloud1.setY(25);
		}
		if(cloud2!= null){
			cloud1.setX(1000); cloud1.setY(100);
		}
		if(cloud3!= null){
			cloud1.setX(1300); cloud1.setY(50);
		}
		//reset sun position
		if(sun!=null){
			sun.setX(s.getWidth()); sun.setY(s.getHeight()/50);
		}
	}
	
	
}
