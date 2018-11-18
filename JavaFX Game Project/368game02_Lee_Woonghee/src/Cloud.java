import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Cloud extends Entity{
	
	private AnimationTimer cloudTimer;
	
	public Cloud(double width, double height, int x, int y){
	
		super(width,height,x,y);
		setImage(new Image("image/cloud.png"));
	}
	
	//this will move the clouds until the game is over
	public void moveClouds(Scene s, double speed){
		cloudTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	if(getX()==-getFitWidth()){
            		setX(s.getWidth());
            	}else{
            		setX(getX()-speed);
            	}
            }
        };
        cloudTimer.start();
	}
	//stop the cloud
	public void stopClouds(){
		cloudTimer.stop();
	}
}
