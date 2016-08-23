package studentempsignin;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainPage_Controller implements Initializable {
	@FXML
	Text clockInText;

	public MainPage_Controller() {

	}

	// this method fixed my problem of changing text before window shows up
	// essentially you are supposed to call the controller from where you loaded
	// the fxml
	// then you call the fields in that controller and then thats when you can
	// change it

	public void signIn(String empID, Button btn) throws IOException,
			SQLException {
		Stage window;
		window = (Stage) btn.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"MainPage_Controller.fxml"));
		Parent root = (Parent) loader.load();
		SignIn_Controller controller = (SignIn_Controller) loader
				.getController();
		Scene scene = new Scene(root, 500, 300);
		window.setScene(scene);

		Connection connect = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/AUStudentEmployees", "kheneahm",
				"kennygoham");

		Statement stmnt = connect.createStatement();
		ResultSet result = stmnt
				.executeQuery("select FirstName, LastName from emp_info"
						+ " where EmployeeID = " + empID + "; ");

		while (result.next()) {
			controller.empName.setText(result.getString(1) + " "
					+ result.getString(2));
			controller.empIDMain.setText(empID);
		}

		window.show();

	}

	@FXML
	public void clockingIn() throws Exception {
		Stage window = new Stage();
		FXMLLoader loader = new FXMLLoader(getClass().getResource(
				"ClockIn_Controller.fxml"));
		Parent root = (Parent) loader.load();
		SignIn_Controller controller = (SignIn_Controller) loader
				.getController();
		Scene scene = new Scene(root);
		window.setScene(scene);

		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date dateobj = new Date();

		
		controller.clockInText.setText(df.format(dateobj));

		window.show();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

	}

}