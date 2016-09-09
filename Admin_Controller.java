package studentempsignin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.prism.paint.Color;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Admin_Controller {

	@FXML
	private Button submitEmpBtn;
	@FXML
	private Button cancelBtn;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button searchBtn;
	@FXML
	private Button searchUpdBtn;
	@FXML 
	private Button updateEmpBtn;
	

	@FXML
	private Text empIDErrorTxt;
	@FXML
	private Text empAddedTxt;
	@FXML
	private Text fNameErrorTxt;
	@FXML
	private Text lNameErrorTxt;
	@FXML
	private Text gNameErrorTxt;
	@FXML
	private Text hidEmpID;
	@FXML
	private Text updateEmpIDError;
	@FXML
	private Text hidEmpIdUpd;
	@FXML
	private Text empUmpSuccess;
	
	@FXML
	private Label empIDDelErrorLbl;
	@FXML
	private Label empInfoLbl;

	@FXML
	private TextField empIDDel;
	@FXML
	private TextField empID;
	@FXML
	private TextField fName;
	@FXML
	private TextField lName;
	@FXML
	private TextField empIDUpd;
	@FXML
	private TextField updateEmpID;
	@FXML
	private TextField updateFName;
	@FXML
	private TextField updateLName;
	
	
	@FXML
	private TextArea empInfo;
	
	@FXML
	private ComboBox<String> groupName;
	@FXML
	private ComboBox<String> updateGroupName;
	
	@FXML
	private CheckBox empIDChckBx;
	@FXML
	private CheckBox fNameChckBx;
	@FXML
	private CheckBox lNameChckBx;
	@FXML
	private CheckBox gNameChckBx;

	SignIn_Controller method;

	public Admin_Controller() {

	}
	@FXML
	public void updateEmp(){
		updateEmpBtn.setOnAction((e) ->{
			try{
				
				Connection connect = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/AUStudentEmployees", "root",
						"password");

				Statement stmnt = connect.createStatement();
				
				method = new SignIn_Controller();

				char[] empIDArray = updateEmpID.getText().toCharArray();
				char[] fNameArray = updateFName.getText().toCharArray();
				char[] lNameArray = updateLName.getText().toCharArray();

			
				//checks if the id is being used in admin/emp id database
				boolean isIDInEmp = method.checkDatabase(updateEmpID
					.getText());
				boolean isIDInAdmin = method.checkDatabaseAdmin(updateEmpID
					.getText());
				//creates new string to pass through the method to check if
				//it contains a letter
				String empIDArrayString = new String(empIDArray); 
				boolean isContainingLetter = checkString(empIDArrayString); //call method

			
				//setting which txt becomes visible depending on the conditions
				boolean isValidEmp = checkEmpID(empIDArray, isContainingLetter, isIDInEmp, isIDInAdmin);
				boolean isValidFName = checkFName(fNameArray, updateFName);
				boolean isValidLName = checkLName(lNameArray, updateLName);
				boolean isValidGName = checkGName(updateGroupName);
				if(isValidEmp == true && isValidFName == true && isValidLName == true &&
					 isValidGName == true) {
					empIDErrorTxt.setText("");
					fNameErrorTxt.setText("");
					lNameErrorTxt.setText("");
					gNameErrorTxt.setText("");
					
					stmnt.executeUpdate("UPDATE emp_info set EmployeeID = '" + updateEmpID.getText() +
							"', FirstName = '" + updateFName.getText() + "', LastName = '" + updateLName.getText()
							+ "', GroupName = '" + updateGroupName.getValue() + "' where EmployeeID = " +
							hidEmpIdUpd.getText() + ";");
					empUmpSuccess.setOpacity(1);
					
			 }
			}
			catch(Exception exc){
				exc.printStackTrace();
			}
		});
	}
	@FXML
	public void deleteEmp(){
		deleteBtn.setOnAction((e) ->{
			if (empInfo.getText() == null ||  hidEmpID.getText() == ""){
				empInfoLbl.setText("No Employee to delete!");
				empInfoLbl.setTextFill(javafx.scene.paint.Color.RED);
				empInfoLbl.setOpacity(1);

			}
			else{
				
				try {
					empInfoLbl.setOpacity(0); 
					Connection connect = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/AUStudentEmployees",
							"root", "password");
					Statement stmnt = connect.createStatement();
					stmnt.executeUpdate("DELETE from emp_info where EmployeeID = '"
									+ hidEmpID.getText() + "';");
					empInfoLbl.setText("Employee Successfully Deleted!");
					empInfoLbl.setTextFill(javafx.scene.paint.Color.GREEN);
					empInfoLbl.setOpacity(1);

					 //set text back to blank so if user clicks delete it will 
					//show error
					hidEmpID.setText(""); 
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
	}
	
	@FXML
	public void searchEmpUpdate() {
		searchUpdBtn.setOnAction((e) -> {
			try {
				method = new SignIn_Controller();

				boolean isIDInEmp = method.checkDatabase(empIDUpd.getText()); //call method from sign in class
				if (isIDInEmp == false) {
					updateEmpIDError.setText("Invalid Emp ID");
					updateEmpIDError.setOpacity(1);
					
				} 
				else {
					updateEmpIDError.setOpacity(0);
					
					hidEmpIdUpd.setText(empIDUpd.getText());
					//empInfo.setEditable(true);
					setFields(empIDUpd.getText());
				
					
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		});

	}
	private void setFields(String ID) throws SQLException {
		String empID = "";
		String fName = "";
		String lName = "";
		String groupName = "";
		Connection connect = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/AUStudentEmployees",
				"root", "password");
		Statement stmnt = connect.createStatement();
		ResultSet result = stmnt
				.executeQuery("select EmployeeID, FirstName,"
						+ " LastName, GroupName from emp_info where"
						+ " EmployeeID = '" + ID + "';");
		while(result.next()){
			empID = result.getString(1);
			fName = result.getString(2);
			lName = result.getString(3);
			groupName = result.getString(4);
		}
		updateEmpID.setText(empID);
		updateEmpID.setDisable(true);
		empIDChckBx.setSelected(true);
							
		updateFName.setText(fName);
		updateFName.setDisable(true);
		fNameChckBx.setSelected(true);
						
		updateLName.setText(lName);
		updateLName.setDisable(true);
		lNameChckBx.setSelected(true);
		
		updateGroupName.setValue(groupName);
		updateGroupName.setDisable(true);
		gNameChckBx.setSelected(true);
		
		
		connect.close();		
	}
	@FXML
	public void selectEmpIDCheckBox(){
		if(!empIDChckBx.isSelected()){
			updateEmpID.setDisable(false);
		}
		else{
			updateEmpID.setDisable(true);
		}
	}
	@FXML
	public void selectFNameCheckBox(){
		if(!fNameChckBx.isSelected()){
			updateFName.setDisable(false);
		}
		else{
			updateFName.setDisable(true);
		}
	}
	@FXML
	public void selectLNameCheckBox(){
		if(!lNameChckBx.isSelected()){
			updateLName.setDisable(false);
		}
		else{
			updateLName.setDisable(true);
		}
	}
	@FXML
	public void selectGroupNameCheckBox(){
		if(!gNameChckBx.isSelected()){
			updateGroupName.setDisable(false);
		}
		else{
			updateGroupName.setDisable(true);
		}
	}

	@FXML
	public void searchEmp() {
		searchBtn.setOnAction((e) -> {
			String empID = "";
			String fName = "";
			String lName = "";
			String groupName = "";
			try {
				method = new SignIn_Controller();

				boolean isIDInEmp = method.checkDatabase(empIDDel.getText()); //call method from sign in class
				if (isIDInEmp == false) {
					empInfo.clear();
					empIDDelErrorLbl.setText("Invalid Emp ID");
					empIDDelErrorLbl.setOpacity(1);
					hidEmpID.setText("");
					
				} 
				else {
					empIDDelErrorLbl.setOpacity(0);
					empInfoLbl.setOpacity(0);
					//empInfo.setEditable(true);
					empInfo.clear();
					Connection connect = DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/AUStudentEmployees",
							"root", "password");
					Statement stmnt = connect.createStatement();
					ResultSet result = stmnt
							.executeQuery("select EmployeeID, FirstName,"
									+ " LastName, GroupName from emp_info where"
									+ " EmployeeID = '" + empIDDel.getText() + "';");
					while(result.next()){
						empID = result.getString(1);
						fName = result.getString(2);
						lName = result.getString(3);
						groupName = result.getString(4);
					}
					hidEmpID.setText(empID); //used in the delete method
					empInfo.setText("Employee ID: " + empID + "\nFirst Name: " + 
									fName + "\nLast Name: " + lName + "\nGroup Name: "
									+ groupName + "\n\n Delete?");
					empInfo.setEditable(false); //disable user edit
					connect.close();
					
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		});

	}

	//method to add emp to database
	@FXML
	public void submitEmp() {
		submitEmpBtn
				.setOnAction((e) -> {

					try {

						method = new SignIn_Controller();

						//
						char[] empIDArray = empID.getText().toCharArray();
						char[] fNameArray = fName.getText().toCharArray();
						char[] lNameArray = lName.getText().toCharArray();

						
						//checks if the id is being used in admin/emp id database
						boolean isIDInEmp = method.checkDatabase(empID
								.getText());
						boolean isIDInAdmin = method.checkDatabaseAdmin(empID
								.getText());
						//creates new string to pass through the method to check if
						//it contains a letter
						String empIDArrayString = new String(empIDArray); 
						boolean isContainingLetter = checkString(empIDArrayString); //call method

						
						//setting which txt becomes visible depending on the conditions
						boolean isValidEmp = checkEmpID(empIDArray, isContainingLetter, isIDInEmp, isIDInAdmin);
						boolean isValidFName = checkFName(fNameArray, fName);
						boolean isValidLName = checkLName(lNameArray, lName);
						boolean isValidGName = checkGName(groupName);
						 if(isValidEmp == true && isValidFName == true && isValidLName == true &&
								 isValidGName == true) {
							empIDErrorTxt.setText("");
							fNameErrorTxt.setText("");
							lNameErrorTxt.setText("");
							gNameErrorTxt.setText("");

							// String[] employeeIDArray;

							//create new employee then set the fields
							Employee emp = new Employee();
							emp.setEmpID(empID.getText());
							emp.setfName(fName.getText());
							emp.setlName(lName.getText());
							emp.setGroupName(groupName.getValue());

							emp.setHourlyWage("8.50");
							emp.setEstHours("0");
							emp.setEstPay("0");
							emp.setIsClockedIn("0");

							Map<String, Employee> mapEmp = new HashMap<>();
							mapEmp.put(empID.getText(), emp);

							Iterator entries = mapEmp.entrySet().iterator(); // get
																				// iterator
																				// from
																				// maps
																				// entry
																				// set
							while (entries.hasNext()) {
								Map.Entry entry = (Map.Entry) entries.next(); // grab
																				// the
																				// entry
								String key = (String) entry.getKey(); // get key
																		// value
								Employee value = (Employee) entry.getValue(); // cast
																				// the
																				// value
																				// as
																				// an
																				// Emp
								// System.out.print("key: " + key + "\n value: "
								// + value.getfName());
								addDatabase(key, value);
								empAddedTxt.setText("Employee Added!");
								empAddedTxt.setOpacity(1);
								
							}

						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				});
	}

	private boolean checkGName(ComboBox<String> groupName) {
		boolean isValid = false;
		if (groupName.getValue() == null) {

			gNameErrorTxt.setText("Choose a group");
			gNameErrorTxt.setOpacity(1);

		}
		else{
			gNameErrorTxt.setOpacity(0);
			isValid = true;
		}
		return isValid;
		
	}
	private boolean checkLName(char[] lNameArray, TextField lName) {
		boolean isValid = false;
		if (lNameArray.length > 15) {

			lNameErrorTxt.setText("Last Name too long");
			lNameErrorTxt.setOpacity(1);
		} 
		else if (lNameArray.length == 0
				|| lName.getText() == null) {

			lNameErrorTxt.setText("Empty Last Name Field");
			lNameErrorTxt.setOpacity(1);

		}
		else{
			lNameErrorTxt.setOpacity(0);
			isValid = true;
		}
		
		return isValid;
		
	}
	private boolean checkFName(char[] fNameArray, TextField fName) {
		boolean isValid = false;
		if (fNameArray.length > 15) {
			fNameErrorTxt.setText("First Name too long");
			fNameErrorTxt.setOpacity(1);
		} 
		else if (fNameArray.length == 0
				|| fName.getText() == null) {

			fNameErrorTxt.setText("Empty First Name Field");
			fNameErrorTxt.setOpacity(1);

		} 
		else{
			fNameErrorTxt.setOpacity(0);
			isValid = true;
		}
		return isValid;
		
	}
	private boolean checkEmpID(char[] empIDArray, boolean isContainingLetter,
			boolean isIDInEmp, boolean isIDInAdmin) {
		boolean isValid = false;
		
		if (empIDArray.length != 4) {

			empIDErrorTxt.setText("Emp ID not 4 characters!");
			empIDErrorTxt.setOpacity(1);

		}

		else if (isContainingLetter == true) {
			empIDErrorTxt.setText("Emp ID contains letters!");
			empIDErrorTxt.setOpacity(1);
		} 
		else if (isIDInEmp == true || isIDInAdmin == true) {
			empIDErrorTxt.setText("ID already being used!");
			empIDErrorTxt.setOpacity(1);

		}
		else{
			empIDErrorTxt.setOpacity(0);
			isValid = true;
		}
		return isValid;
		
	}
	//method for adding to database
	//simple use of statements and setting GroupName, Pay, Hours and ClockedIn with
	//default values
	private void addDatabase(String key, Employee emp) throws SQLException {
		Connection connect = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/AUStudentEmployees", "root",
				"password");

		Statement stmnt = connect.createStatement();
		stmnt.executeUpdate("INSERT INTO emp_info (EmployeeID, FirstName, LastName,"
				+ " GroupName, HourlyWage, EstimatedHours, EstimatedPay, isClockedIn) "
				+ "VALUES ('"
				+ emp.getEmpID()
				+ "','"
				+ emp.getfName()
				+ "', '"
				+ emp.getlName()
				+ "', '"
				+ emp.getGroupName()
				+ "', '8.50', " + "'0', '0', '0')");
		stmnt.executeUpdate("INSERT INTO emp_hours (EmployeeID, clockInTime, clockOutTime,"
				+ " differenceInMinutes) VALUES ('" + emp.getEmpID() + "', '0','0','0');");

		connect.close();

	}

	private boolean checkString(String string) {
		try {
			Integer checkID = Integer.parseInt(string);

		}
		// if we parse through the string and it contains a letter
		// it wont be able to parse it and will throw and exception
		catch (NumberFormatException exc) {
			return true;
		}
		return false;
	}

	@FXML
	public void cancel() {
		cancelBtn.setOnAction((e) -> {
			try {
				method = new SignIn_Controller();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			method.closeCurrentWindow(cancelBtn);
		});
	}
	
	public void addEmpWindow() throws IOException {
		Stage stage = new Stage();
		FXMLLoader load = new FXMLLoader();
		load.setLocation(Admin_Controller.class.getResource("addEmpPage.fxml"));
		Parent root = (Parent) load.load();
		Scene scn = new Scene(root);
		stage.setScene(scn);
		stage.show();

	}

	public void delEmpWindow() throws IOException {
		Stage stage = new Stage();
		FXMLLoader load = new FXMLLoader();
		load.setLocation(Admin_Controller.class.getResource("delEmpPage.fxml"));
		Parent root = (Parent) load.load();
		Scene scn = new Scene(root);
		stage.setScene(scn);
		stage.show();
	}
	public void updateEmpWindow() throws IOException {
		Stage stage = new Stage();
		FXMLLoader load = new FXMLLoader();
		load.setLocation(Admin_Controller.class.getResource("updateEmpPage.fxml"));
		Parent root = (Parent) load.load();
		Scene scn = new Scene(root);
		stage.setScene(scn);
		stage.show();
		
	}

}
