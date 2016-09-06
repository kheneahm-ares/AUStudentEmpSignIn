package studentempsignin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DigitalClock{

	public DigitalClock(Text time){
		setTime(time);
	}

	 public void setTime(Text time) {
			
		    Timeline timeline = new Timeline(
		      new KeyFrame(Duration.seconds(0),
		        new EventHandler<ActionEvent>() {
		          
				@Override 
				public void handle(ActionEvent actionEvent) {
		        	  	
		             	DateFormat df = new SimpleDateFormat("HH:mm:ss");
		             	Date dateobj = new Date();
						time.setText(df.format(dateobj));
						
		          }
		        }
		      ),
		      new KeyFrame(Duration.seconds(1))
		    );
		    timeline.setCycleCount(Animation.INDEFINITE);
		    timeline.play();
		  }
}
