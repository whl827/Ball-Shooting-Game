import javafx.scene.image.Image;

public class Sky extends Entity{

	public Sky(double width, double height){
		super(width,height);
		setImage(new Image("image/sky.png"));
	}
	public Sky(double width, double height, int x, int y){
		super(width,height,x,y);
		setImage(new Image("image/sky.png"));
	}
}
