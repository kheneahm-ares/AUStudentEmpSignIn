/*
 * This class contains a function that executes if the user clicks the sign in button
Note: this class implements tryCatch, fxml, event handling, and exceptions 
*/
package studempsignin;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 *
 * @author kheneahmares
 */
public class SignIn_Controller {
    @FXML
    private Button signInBtn;
    @FXML
    private TextField empIDField;
    @FXML
    private Text invalidID;
    @FXML 
    private TextArea empInfo;
    
    private Connection connection;
    
    
    public SignIn_Controller() throws IOException{
        
    }
    
    public void signIn() throws Exception{
        signInBtn.setOnAction((ActionEvent e) -> {
            try {
                DBaseDriver connect = new DBaseDriver("jdbc:mysql://localhost:3306/AUStudentEmployees",
                        "kheneahm", "kennygoham");
                String empID = empIDField.getText();
                signingIn(empID);
                
            } catch (SQLException ex) {
                Logger.getLogger(SignIn_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
             
        });
    }

    private void signingIn(String empID) throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/AUStudentEmployees", "kheneahm",
                "kennygoham");

        while(true){

        boolean isIDInDatabase = checkDatabase(empID);
        
        if(isIDInDatabase == false){
            invalidID.setOpacity(1);
        }
        else{
            break;
        }
        }

        
        //create statement
        Statement statement = connection.createStatement();
        
        //convert stringID to string to make the query valid!
        
        //holds the result of the executed query
        ResultSet resultSet = statement.executeQuery("select EmployeeID, FirstName, LastName from emp_info where EmployeeID "
                + "= '" + empID + "'");

        //iterate through the result and print the statements
        while (resultSet.next()) {
            empInfo.setText("Employee ID: " + resultSet.getString(1) + " is "
                    + resultSet.getString(2) + " " + resultSet.getString(3));
            
        }

        connection.close();

    }

    //method to check database for empID
    private boolean checkDatabase(String empID) throws SQLException{
        boolean isInDatabase = false;
        int i = 0;
        int inArray = 0;
        String[] empIDs = getEmpIDFromDatabase();
        
        while(i < empIDs.length){
            if(empID.equals(empIDs[i])){
                 inArray++;
            }
            i++;
        }
        if(inArray != 0){
            isInDatabase = true;
              }
        return isInDatabase;
    }

    private String[] getEmpIDFromDatabase() throws SQLException {
        Set<String> arraySet = new HashSet<String>();
        String[] arrayID;
         Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/AUStudentEmployees",
                                "kheneahm", "kennygoham");
         
         String query = "select EmployeeID from emp_info;";
         
         Statement stmnt = connect.createStatement();
         
         ResultSet rslt = stmnt.executeQuery(query);
         
         while(rslt.next()){
             arraySet.add(rslt.getString("EmployeeID")); // grabs ID from database
         }
         
         stmnt.close();
         
         //returns iterator from arraySet
         Iterator<String> iterator = arraySet.iterator();
         
         //create an array with a fixed size
         arrayID = new String[arraySet.size()];
         int i = 0;
         while(iterator.hasNext()){
             arrayID[i] = iterator.next();
             i++;
        
            }
         return arrayID;
    }
    
}
