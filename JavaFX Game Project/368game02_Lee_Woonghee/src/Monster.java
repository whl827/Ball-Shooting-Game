import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Monster extends Entity{

	public Monster(double width, double height, double x, double y){
		super(width, height, x, y);
	}
	//rotate
	public	void rotateAround(Circle c){	
		PathTransition pt	=	new	PathTransition();	
		pt.setDuration(Duration.millis(4000));	
		pt.setPath(c);	
		pt.setNode(this);
		pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);	
		pt.setCycleCount(Timeline.INDEFINITE);	
		pt.setAutoReverse(true);	
		pt.play();
	}
	
}
