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
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javafx.scene.control.Label;
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
	
	//admin buttons
	@FXML
	private Button signOutAdmin;
	@FXML
	private Button addEmpBtn;
	@FXML
	private Button delEmpBtn;
	@FXML
	private Button updEmpBtn;

	//emp fields
	@FXML
	private Button signInBtn;
	@FXML
	private Button clockInBtn;
	@FXML
	private Button clockOutBtn;
	@FXML
	private Button estimatedHoursBtn;
	@FXML
	private Button signOutBtn;
	@FXML
	private Button payBtn;


	@FXML
	private TextField empIDField;
	@FXML
	private Text invalidID;
	@FXML
	private Text clockInError;
	@FXML
	private Text clockOutError;
	@FXML
	private Text payInfo;

	@FXML
	Text empIDMain;
	@FXML
	Text empIDHours;
	@FXML
	Text empName;
	@FXML
	Text clockInText;
	@FXML
	Text estimateHoursText;
	@FXML
	Text timeText;
	@FXML
	Text adminGroupText;

	MainPage_Controller method;
	
	Admin_Controller adminMethod;

	private Connection connection;

	String empID;

	public SignIn_Controller() throws IOException {

	}
	
	@FXML
	public void updateEmp(){
		updEmpBtn.setOnAction((e)->{
			adminMethod = new Admin_Controller();
			try {
				adminMethod.updateEmpWindow();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	//method for delete button in admin main page
	//calls method from admin controller class
	@FXML
	public void deleteEmp(){
		delEmpBtn.setOnAction((e) ->{
			try {
				adminMethod = new Admin_Controller();
				adminMethod.delEmpWindow();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	//method for add button in admin main page
	//calls method from admin controller class
	@FXML
	public void addEmp(){
		addEmpBtn.setOnAction((e) -> {
			
			try {
				adminMethod = new Admin_Controller();
				adminMethod.addEmpWindow();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	@FXML
	public void signOutAdmin(){
		signOutAdmin.setOnAction((e) -> {

			try {

				closeCurrentWindow(signOutAdmin);
				loadSignInPage();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}
	
	// method to calculate pay when paybtn is clicked
	@FXML
	public void calcPay() {
		payBtn.setOnAction((e) -> {
			try {
				String empID = empIDHours.getText();
				connection = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/AUStudentEmployees",
						"root", "password");

				Statement stmnt = connection.createStatement();
				ResultSet result = stmnt
						.executeQuery("select EstimatedPay from emp_info where EmployeeID = '"
								+ empID + "';");

				while (result.next()) {
					payInfo.setText(result.getString(1)); // sets text
					payInfo.setOpacity(1); // makes visible
				}
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	// method for when the estimatedhoursbtn is clicked
	// will show the hoursinfo.fxml page and calls the estimate hours method in
	// mainpage_controller
	@FXML
	public void estimateHours() {
		estimatedHoursBtn.setOnAction((e) -> {
			try {
				clockInError.setOpacity(0);
				clockOutError.setOpacity(0);
				String empID = empIDMain.getText();
				method = new MainPage_Controller();
				method.estimateHours(empID);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	// method for signing out
	// needed because a person can sign in but if it clocks out(since clock out
	// also is used
	// ..for signing out) without clocking in, it will throw an exception since
	// the estimated minutes will be negative and will be a "long"
	@FXML
	public void signOut() {
		signOutBtn.setOnAction((e) -> {

			try {

				closeCurrentWindow(signOutBtn);
				loadSignInPage();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	}

	// clocks out
	@FXML
	public void clockOut() {
		clockOutBtn.setOnAction((e) -> {
			try {
				String empID = empIDMain.getText();

				String clockedInValue = checkStatus(empID); // call method to
															// check status

				if (clockedInValue.equalsIgnoreCase("0")) {
					clockInError.setOpacity(0);
					clockOutError.setOpacity(1); // cant clock out without
													// clocking in
				} else {
					// clockOutError.setOpacity(0);
					// DateFormat df = new SimpleDateFormat("HH:mm:ss");
					Date dateobj = new Date();// store value
					Long timeClockedOut = dateobj.getTime();
					clockTime(empID, timeClockedOut, false);
					setDifferenceInTime(empID); // after getting clock out, you
												// are now ready to calculate
												// difference
					setHours(empID);
					setPay(empID);
					resetTime(empID); // reset everything to 0

					changeStatus(clockedInValue, empID);
					closeCurrentWindow(clockOutBtn); // closes window
					loadSignInPage();
				}
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
				// used to get name to check status but was too vague
				// needed to use empID...
				// thats why i hid an empID in main page

				// String[] wholeName = empName.getText().split(" ");
				// String fName = wholeName[0];
				// String lName = wholeName[1];

				String empID = empIDMain.getText();

				String clockedInValue = checkStatus(empID); // call method and
															// store value

				if (clockedInValue.equalsIgnoreCase("1")) {
					clockOutError.setOpacity(0);
					clockInError.setOpacity(1);

				} else {
					clockInError.setOpacity(0);
					clockOutError.setOpacity(0);
					method = new MainPage_Controller();
					long timeClockedIn = method.clockingIn();
					clockTime(empID, timeClockedIn, true);
					changeStatus(clockedInValue, empID);
				}

			} catch (Exception ex) {
				Logger.getLogger(SignIn_Controller.class.getName()).log(
						Level.SEVERE, null, ex);
			}

		});
	}

	private void clockTime(String empID, long timeClocked, boolean isClockingIn)
			throws SQLException {
		String stringClocked = Long.toString(timeClocked);

		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/AUStudentEmployees", "root",
				"password");

		Statement stmnt = connection.createStatement();
		if (isClockingIn == true) {
			stmnt.executeUpdate("UPDATE emp_hours set clockInTime = '"
					+ stringClocked + "' where EmployeeID = '" + empID + "';");
		} else {
			stmnt.executeUpdate("UPDATE emp_hours set clockOutTime = '"
					+ stringClocked + "' where EmployeeID = '" + empID + "';");
		}

	}

	private void changeStatus(String clockedInValue, String empID)
			throws SQLException {
		Statement stmnt = connection.createStatement();

		if (clockedInValue.equalsIgnoreCase("1")) {
			stmnt.executeUpdate("UPDATE emp_info set isClockedIn = 0 where EmployeeID = "
					+ empID + ";");

		} else {
			stmnt.executeUpdate("UPDATE emp_info set isClockedIn = 1 where EmployeeID = "
					+ empID + ";");
		}
		connection.close();

	}

	// method to check whether the user is already signed in!
	// using firstname and lastname as conditions in query
	private String checkStatus(String empID) throws SQLException {
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/AUStudentEmployees", "root",
				"password");

		Statement stmnt = connection.createStatement();
		// ResultSet result = stmnt
		// .executeQuery("select isClockedIn from emp_info where "
		// + "FirstName = '" + fName + "' AND LastName = '"
		// + lName + "';");

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

	public void signingIn(String ID) throws SQLException, Exception {
		// connect to database via connection type so you can createStatement
		connection = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/AUStudentEmployees", "kheneahm",
				"kennygoham");

		boolean isEmpIDInDatabase = checkDatabase(ID); // calls method to check
														// database for empId	
		boolean isAdminIDInDatabase = checkDatabaseAdmin(ID);

		if (isEmpIDInDatabase == false && isAdminIDInDatabase == false) { // if id is not in database... invalidID!
			// empInfo.clear();
			invalidID.setOpacity(1);

		}

		else if (isEmpIDInDatabase == true){
			method.signIn(ID, signInBtn);

			connection.close();
		}
		else{
			method.signInAdmin(ID, signInBtn);
			connection.close();
		}
	}

	// method to check database for empID
	protected boolean checkDatabase(String empID) throws SQLException {
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
	
	// method to check database for empID
	protected boolean checkDatabaseAdmin(String adminID) throws SQLException {
		boolean isInDatabase = false;
		int i = 0;
		int inArray = 0;
		String[] empIDs = getAdminFromDatabase();

		while (i < empIDs.length) {
			if (adminID.equals(empIDs[i])) {
				inArray++;
			}
			i++;
		}
		if (inArray != 0) {
			isInDatabase = true;
		}
		return isInDatabase;
	}

	protected String[] getAdminFromDatabase() throws SQLException {
		Set<String> adminID = new HashSet<String>();
		String[] arrayID;

		Connection connect = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/AUStudentEmployees", "root",
				"password");
		String query = "select AdminID from admin_info";
		Statement stmnt = connect.createStatement();
		ResultSet result = stmnt.executeQuery(query);

		while (result.next()) {
			adminID.add(result.getString("AdminID")); // or at column 1
		}

		stmnt.close();
		
		Iterator<String> iterator = adminID.iterator();
		arrayID = new String[adminID.size()];
		
		int i = 0;
		while(iterator.hasNext()){
			arrayID[i] = iterator.next();
			i++;
		}
		
		connect.close();
		
		return arrayID;
		
		

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
		connect.close();
		return arrayID;
	}

	protected void closeCurrentWindow(Button btn) {
		// get current window scene
		Stage stage = (Stage) btn.getScene().getWindow();
		stage.close();

	}

	private void setPay(String empID) throws SQLException {
		String minutes = null;
		Statement stmnt = connection.createStatement();
		ResultSet result = stmnt
				.executeQuery("select EstimatedHours from emp_info where EmployeeID = '"
						+ empID + "';");

		while (result.next()) {
			minutes = result.getString(1);
		}
		double intMinutes = Double.parseDouble(minutes);
		double pay = (Double) (intMinutes * (8.5)); // divided to get pay per
													// hour //cast double to set
													// precision to 2 decimals
		DecimalFormat two = new DecimalFormat("#0.00");
		// String stringPay = Double.toString(pay);

		stmnt.executeUpdate("UPDATE emp_info set EstimatedPay = '$"
				+ two.format(pay) + "' where " + "EmployeeID = '" + empID
				+ "';");

		stmnt.close();

	}

	// sets hours
	private void setHours(String empID) throws SQLException {
		String minutes = null;
		String currentHours = null;
		double hours = 0;
		Statement stmnt = connection.createStatement();
		ResultSet resultMinutes = stmnt
				.executeQuery("select differenceInMinutes from emp_hours where EmployeeID = '"
						+ empID + "';"); // grabs minutes

		while (resultMinutes.next()) {
			minutes = resultMinutes.getString(1);
		} // gets minutes from result

		ResultSet resultHours = stmnt
				.executeQuery("select EstimatedHours from emp_info where EmployeeID = '"
						+ empID + "';"); // grabs hours

		while (resultHours.next()) {
			currentHours = resultHours.getString(1); // gets currentHours from
														// emp_info
		}
		int intMinutes = Integer.parseInt(minutes); // sets minutes string to
													// int

		hours = Double.parseDouble(currentHours); // sets currenthours string to
													// double
		hours += (Double) (intMinutes / 60.0); // cast double to set precision
												// to 2 decimals
												// reason why im using += is to
												// not reset
												// the hours everytime but
												// instead to keep adding since
												// it'll be a two week reset
												// from admin

		DecimalFormat two = new DecimalFormat("#0.00"); // format to set double
														// precision to only two
														// decimals
														// doing this bc if not,
														// the double will be
														// too long for the sql
														// field

		// String stringHours = Double.toString(hours);
		stmnt.executeUpdate("UPDATE emp_info set EstimatedHours = '"
				+ two.format(hours) + "' where " + "EmployeeID = '" + empID
				+ "';");

	}

	private void resetTime(String empID) throws SQLException {
		Statement stmnt = connection.createStatement();
		stmnt.executeUpdate("UPDATE emp_hours set clockInTime = 0 where EmployeeID = '"
				+ empID + "';");
		stmnt.executeUpdate("UPDATE emp_hours set clockOutTime = 0 where EmployeeID = '"
				+ empID + "';");
		stmnt.executeUpdate("UPDATE emp_hours set differenceInMinutes = 0 where EmployeeID = '"
				+ empID + "';");

	}

	// get the difference from clockedIn time and clockOut time

	private void setDifferenceInTime(String empID) throws SQLException {
		Statement stmnt = connection.createStatement();
		ResultSet result = stmnt
				.executeQuery("select clockInTime, clockOutTime from emp_hours where EmployeeID = '"
						+ empID + "';");
		String time[] = new String[2];

		while (result.next()) {
			time[0] = result.getString(1); // clockInTime
			time[1] = result.getString(2); // clockOutTime

		}

		long clockInTime = Long.parseLong(time[0]); // changes strings to long
		long clockOutTime = Long.parseLong(time[1]);

		int difference = (int) (clockOutTime - clockInTime); // gets difference
		int differenceInMinutes = difference / (1000 * 60); // calculates
															// difference in
															// minutes

		String stringDifference = Long.toString(differenceInMinutes); // changes
																		// long
																		// to
																		// string
																		// for
																		// query
																		// usage

		stmnt.executeUpdate("UPDATE emp_hours set differenceInMinutes = '"
				+ stringDifference + "' where EmployeeID = '" + empID + "';");
	}

	protected void loadSignInPage() throws IOException {
		Stage stg = new Stage();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(SignIn_Controller.class.getResource("RootGUI.fxml"));
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

	@Override
	public void initialize(java.net.URL location, ResourceBundle resources) {

	}

}
