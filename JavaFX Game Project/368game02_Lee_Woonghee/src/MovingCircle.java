import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class MovingCircle extends Circle {
	private double xSpeed;
	private double ySpeed;

	public MovingCircle(double centerX, double centerY, double radius) {
		super(centerX, centerY, radius);
		xSpeed = 0;
		ySpeed = 1;
		setFill(Color.ORANGE);
	}
	public void changeDirectionX(){
		xSpeed *= -1;
	}
	public void changeDirectionY(){
		ySpeed *= -1;
	}
	//keep update x and y in order to move
	public void update(){
		this.setCenterX(this.getCenterX() + xSpeed);
		this.setCenterY(this.getCenterY() + ySpeed);
	}
	//getters, setters
	public void setXSpeed(double speed){
		xSpeed += speed;
	}
	public void setYSpeed(double speed){
		ySpeed += speed;
	}
	public double getXSpeed(){
		return xSpeed;
	}
	public double getYSpeed(){
		return ySpeed;
	}
	public void clear(){
		this.clear();
	}
	
}