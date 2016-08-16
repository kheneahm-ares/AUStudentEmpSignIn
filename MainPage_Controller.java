package studentempsignin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPage_Controller {
	
	
	
	public MainPage_Controller(){
		
	}
	
	//this method fixed my problem of changing text before window shows up
	//essentially you are supposed to call the controller from where you loaded the fxml
	//then you call the fields in that controller and then thats when you can change it
	public void signIn(String empID, Button btn) throws IOException, SQLException{
	    Stage window; 
	    window =(Stage) btn.getScene().getWindow();
	    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainPage_Controller.fxml"));
	    Parent root = loader.load();
	    SignIn_Controller controller = (SignIn_Controller) loader.getController();
	    Scene scene = new Scene(root,500,200);
	    window.setScene(scene);
	    
	    Connection connect =  DriverManager.getConnection("jdbc:mysql://localhost:3306/AUStudentEmployees", "kheneahm",
	            "kennygoham");
	    
	    Statement stmnt = connect.createStatement();
	    ResultSet result = stmnt.executeQuery("select FirstName, LastName from emp_info"
	    		+ " where EmployeeID = " + empID + "; ");
	    
	    while(result.next()){
	    	 controller.empName.setText(result.getString(1) + " " + result.getString(2));
	    }

	    window.show();
		
	}
	
	

}
