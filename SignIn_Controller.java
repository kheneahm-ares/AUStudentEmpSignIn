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
	private Text clockInError;
	
	
	@FXML
	Text empIDMain;
	@FXML
	Text empName;
	@FXML
	Text clockInText;

	MainPage_Controller method;

	private Connection connection;
	
	String empID;

	public SignIn_Controller() throws IOException {

	}

	@FXML
	public void clockOut() {
		clockOutBtn.setOnAction((e) -> {
			try {
				closeCurrentWindow(clockOutBtn); // closes window
				Stage stg = new Stage();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(SignIn_Controller.class
						.getResource("RootGUI.fxml"));
				BorderPane root = (BorderPane) loader.load();

				Scene scn = new Scene(root);
				stg.setScene(scn);
				stg.show();

				FXMLLoader load = new FXMLLoader();
				load.setLocation(SignIn_Controller.class
						.getResource("ShowControlGUI.fxml"));
				AnchorPane controls = (AnchorPane) load.load();
				root.setCenter(controls);
			}

			catch (Exception exc) {
				exc.printStackTrace();
			}
		});
	}

	@FXML
	public void clockIn() {
		clockInBtn.setOnAction((e) -> {

			try {
				//used to get name to check status but was too vague
				//needed to use empID...
				//thats why i hid an empID in main page 
				
//				String[] wholeName = empName.getText().split(" ");
//				String fName = wholeName[0];
//				String lName = wholeName[1];
				
				String empID = empIDMain.getText();

				String clockedInValue = checkStatus(empID); //call method and store value

				if (clockedInValue == "1") {
					clockInError.setOpacity(1);
					
				} 
				else{
				method = new MainPage_Controller();
				method.clockingIn();
				changeStatus(clockedInValue, empID);
				// can use these couple lines of code if you want to change
				// current window
				// with the new one; didnt want to use it bc I didnt want
				// the current window
				// to disappear, just a new window pop up
				// Stage window;
				// window = (Stage) clockInBtn.getScene().getWindow();
				}

		} catch (Exception ex) {
			Logger.getLogger(SignIn_Controller.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	})	;
	}

	private void changeStatus(String clockedInValue, String empID) throws SQLException {
		Statement stmnt = connection.createStatement();
		
		if(clockedInValue == "1"){
			stmnt.executeUpdate("UPDATE emp_info set isClockedIn = 0 where EmployeeID = "
						+ empID + ";");

		}
		else{
			stmnt.executeUpdate("UPDATE emp_info set isClockedIn = 1 where EmployeeID = "
					+ empID + ";");
		}
	
}
//method to check whether the user is already signed in!
	//using firstname and lastname as conditions in query
	private String checkStatus(String empID) throws SQLException {
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/AUStudentEmployees", "root",
				"password");

		Statement stmnt = connection.createStatement();
//		ResultSet result = stmnt
//				.executeQuery("select isClockedIn from emp_info where "
//						+ "FirstName = '" + fName + "' AND LastName = '"
//						+ lName + "';");
		
		ResultSet result = stmnt
				.executeQuery("select isClockedIn from emp_info where "
						+ "EmployeeID = '" + empID + "';");

		String statusValue = null;

		while (result.next()) {
			statusValue = result.getString(1);
		}

		return statusValue;

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
				empID = empIDField.getText();

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
