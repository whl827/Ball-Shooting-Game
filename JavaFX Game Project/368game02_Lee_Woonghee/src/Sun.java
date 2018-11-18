import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Sun extends Entity{
	
	private AnimationTimer sunTimer;
	
	public Sun(double width, double height, double x, double y){
		
		super(width,height,x,y);
		setImage(new Image("image/sun.png"));
	}
	//this will move the clouds until the game is over
	public void moveSun(Scene s, double speed){
		sunTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
            	if(getX()==-getFitWidth()){
            		sunTimer.stop();
            	}else{
            		setX(getX()-speed);
            	}
            }
        };
        sunTimer.start();
	}
	//stop the cloud
	public void stopSun(){
		sunTimer.stop();
	}
}
