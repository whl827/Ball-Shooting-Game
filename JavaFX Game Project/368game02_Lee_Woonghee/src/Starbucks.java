import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Starbucks extends Entity{
	
	protected Circle mPath;
	
	public Starbucks(double width, double height, int x, int y){
		super(width,height,x,y);
		mPath = new Circle (300, 300, 75);
		setImage(new Image("image/starbucks.png"));
	}
	
	//move around in circle path
	public void move(int duration){
		PathTransition movePath = new PathTransition();
		movePath.setDuration(Duration.millis(duration));
		//create path
		movePath.setPath(mPath);
		movePath.setInterpolator(Interpolator.LINEAR);
		movePath.setCycleCount(Timeline.INDEFINITE);
		movePath.setNode(this);	
		movePath.play();
	}
	public void setCircleLocation(double x, double y){
		mPath.setCenterX(x);
		mPath.setCenterY(y);
	}
	public void setCircleSize(double radius){
		mPath.setRadius(radius);
	}
	
	

	
	
}
