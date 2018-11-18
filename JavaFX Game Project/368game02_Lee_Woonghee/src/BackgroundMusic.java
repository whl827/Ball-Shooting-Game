import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BackgroundMusic{

	private Media media;
	private MediaPlayer mediaPlayer;
	//set up music
	public BackgroundMusic(){
		media = new Media(getClass().getResource("bgm.mp3").toString());
	    mediaPlayer = new MediaPlayer(media);
	}
	public MediaPlayer getMusic(){
		return mediaPlayer;
	}
	//play music
	public void playMusic(){
		mediaPlayer.play();
		mediaPlayer.setCycleCount(javafx.scene.media.MediaPlayer.INDEFINITE);
	}
	//stop music
	public void stopMusic(){
		mediaPlayer.stop();
	}
}
