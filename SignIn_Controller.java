/*
 * This class contains a function that executes if the user clicks the sign in button
Note: this class implements tryCatch, fxml, event handling, and exceptions 
 */
package studentempsignin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


import javax.print.DocFlavor.URL;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author kheneahmares
 */
public class SignIn_Controller extends MainPage_Controller implements
		Initializable {

	@FXML
	private AnchorPane controlData;
	@FXML
	private BorderPane rootLayout;

	@FXML
	private Button signInBtn;
	@FXML
	private Button clockInBtn;
	@FXML
	private Button clockOutBtn;
	@FXML
	private Button estimatedHoursBtn;

	@FXML
	private TextField empIDField;
	@FXML
	private Text invalidID;

	@FXML
	Text empName;
	@FXML
	Text clockInText;
	
	MainPage_Controller method;

	private Connection connection;

	public SignIn_Controller() throws IOException {

	}
	
	@FXML
	public void clockIn(){
		clockInBtn.setOnAction((e)->{
			
			try{
				method = new MainPage_Controller();
				method.clockingIn();
				//can use these couple lines of code if you want to change current window
				//with the new one; didnt want to use it bc I didnt want the current window 
				//to disappear, just a new window pop up
//				Stage window;
//				window = (Stage) clockInBtn.getScene().getWindow();

			}
			 catch (Exception ex) {
					Logger.getLogger(SignIn_Controller.class.getName()).log(
							Level.SEVERE, null, ex);
				}
		});
	}

	// this method is called by the fxml
	@FXML
	public void signIn() throws SQLException {

		signInBtn.setOnAction((e) -> {
			try {
				// used dbaseDriver...idk why
				// DBaseDriver connect = new
				// DBaseDriver("jdbc:mysql://localhost:3306/AUStudentEmployees",
				// "kheneahm", "kennygoham");
				String empID = empIDField.getText();

				method = new MainPage_Controller();
				empIDField.setText("0001");

				signingIn(empID);
			} catch (Exception ex) {
				Logger.getLogger(SignIn_Controller.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		});
	}
	
	
	
	public void signingIn(String empID) throws SQLException, Exception {
		// connect to database via connection type so you can createStatement
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/AUStudentEmployees", "kheneahm",
				"kennygoham");

		boolean isIDInDatabase = checkDatabase(empID); // calls method to check
														// database for empId

		if (isIDInDatabase == false) { // if id is not in database... invalidID!
			// empInfo.clear();
			invalidID.setOpacity(1);

		}

		else {
			method.signIn(empID, signInBtn);

			connection.close();
		}
	}

	// method to check database for empID
	private boolean checkDatabase(String empID) throws SQLException {
		boolean isInDatabase = false;
		int i = 0;
		int inArray = 0;
		String[] empIDs = getEmpIDFromDatabase();

		while (i < empIDs.length) {
			if (empID.equals(empIDs[i])) {
				inArray++;
			}
			i++;
		}
		if (inArray != 0) {
			isInDatabase = true;
		}
		return isInDatabase;
	}

	private String[] getEmpIDFromDatabase() throws SQLException {
		Set<String> arraySet = new HashSet<String>();
		String[] arrayID;
		Connection connect = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/AUStudentEmployees", "kheneahm",
				"kennygoham");

		String query = "select EmployeeID from emp_info;";

		Statement stmnt = connect.createStatement();

		ResultSet rslt = stmnt.executeQuery(query);

		while (rslt.next()) {
			arraySet.add(rslt.getString("EmployeeID")); // grabs ID from
														// database
		}

		stmnt.close();

		// returns iterator from arraySet
		Iterator<String> iterator = arraySet.iterator();

		// create an array with a fixed size
		arrayID = new String[arraySet.size()];
		int i = 0;
		while (iterator.hasNext()) {
			arrayID[i] = iterator.next();
			i++;

		}
		return arrayID;
	}

	private void closeCurrentWindow(Button btn) {
		// get a handle to the stage
		Stage stage = (Stage) btn.getScene().getWindow();
		// do what you have to do
		stage.close();

	}

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}
