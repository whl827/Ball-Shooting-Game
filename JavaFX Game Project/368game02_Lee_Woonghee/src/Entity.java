import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Entity extends ImageView{
	
	private Timeline timeline;
	private KeyValue kv; 
	private KeyFrame kf;

	
	public Entity(){
		timeline = new Timeline();
		
	} //default constructor
	public Entity(double width, double height){
		//set size of images
		this();
		setFitWidth(width); setFitHeight(height);
	}
	//set size and location
	public Entity(double width, double height, double x, double y){
		this(width, height);
		setX(x); setY(y);
	}
	//set image
	public void setImage(String url){
		setImage(new Image(url));
	}
	//move up and down
	public void moveUpAndDown(int duration, Scene s){
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		kv = new KeyValue(yProperty(), s.getHeight()/2, Interpolator.EASE_BOTH);
		kf = new KeyFrame(Duration.millis(duration), kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}
	//move left and right
	public void moveLeftAndRight(int duration, Scene s){
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		kv = new KeyValue(xProperty(), s.getWidth()/4, Interpolator.EASE_BOTH);
		kf = new KeyFrame(Duration.millis(duration), kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}

}
