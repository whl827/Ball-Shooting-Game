import java.util.ArrayList;

import javafx.scene.image.Image;

public class Target extends Entity{
	
	public Target(double width, double height, int x, int y){
		super(width,height,x,y);
		//default image
		setImage(new Image("image/usc.png"));
	}
	public void setLocation(double x, double y){
		setX(x); setY(y);
	}
}
