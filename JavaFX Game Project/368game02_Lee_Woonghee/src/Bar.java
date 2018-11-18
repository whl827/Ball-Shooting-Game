import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Bar extends Rectangle{

	private Timeline timeline;
	private KeyValue kv; 
	private KeyFrame kf;
	
	public Bar(int width, int height, int x, int y, Color color){
		
		super(width, height, x, y);
		setFill(color);
		timeline = new Timeline();
	}
	
	public void moveUpAndDown(int duration, Scene s){
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		kv = new KeyValue(yProperty(), s.getHeight()/2, Interpolator.EASE_BOTH);
		kf = new KeyFrame(Duration.millis(duration), kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}
	
	public void moveLeftAndRight(int duration, Scene s){
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setAutoReverse(true);
		kv = new KeyValue(xProperty(), s.getWidth()/2, Interpolator.EASE_BOTH);
		kf = new KeyFrame(Duration.millis(duration), kv);
		timeline.getKeyFrames().add(kf);
		timeline.play();
	}
}
