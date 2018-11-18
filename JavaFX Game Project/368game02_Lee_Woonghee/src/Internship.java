import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Internship extends Entity{
	
	
	public Internship(double width, double height){
		super(width, height);
	}
	//image fade in/out
	public	void fadeInAndOut(){	
		//choose random time to fade in and out
		Random rand = new Random();
		int x = rand.nextInt(6)+1;
		FadeTransition ft	=	new	FadeTransition(Duration.millis(x*1000), this);	
		ft.setFromValue(1.0);
		ft.setToValue(0.01);	
		ft.setCycleCount(Timeline.INDEFINITE);	
		ft.setAutoReverse(true);	
		ft.play();	
	}

}
