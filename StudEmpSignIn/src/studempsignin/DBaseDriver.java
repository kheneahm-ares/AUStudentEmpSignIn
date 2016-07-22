/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author kheneahmares
 */
public class DBaseDriver {

    private Connection connect;
    

    public DBaseDriver(String dbURL, String user, String password) throws SQLException {

        connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/AUStudentEmployees", "kheneahm",
                "kennygoham");
    }

    public void shutdown() throws SQLException {
        if (connect != null) {
            connect.close();
        }
    }
//
    //These three methods were for testing purposes...now integerated in SignIn_Controller
//    public void printTest(String empID) throws SQLException {
//        
//        //create scanner
////        Scanner input = new Scanner(System.in);
////        String empID;
//        
//        while(true){
//        //prompt user to enter ID
////        System.out.println("Enter Employee ID: ");
////        empID = input.next();
//        
//        //check if the ID is valid
//        boolean isIDInDatabase = checkDatabase(empID);
//        
//        if(isIDInDatabase == false){
//            System.out.println("Enter valid ID!");
//        }
//        else{
//            break;
//        }
//        }
//
//        
//        //create statement
//        Statement statement = connect.createStatement();
//        
//        //convert stringID to string to make the query valid!
//        
//        //holds the result of the executed query
//        ResultSet resultSet = statement.executeQuery("select EmployeeID, FirstName, LastName from emp_info where EmployeeID "
//                + "= '" + empID + "'");
//
//        //iterate through the result and print the statements
//        while (resultSet.next()) {
//            System.out.println("Employee ID: " + resultSet.getString(1) + " is "
//                    + resultSet.getString(2) + " " + resultSet.getString(3));
//        }
//
//        connect.close();
//
//    }
//
//    //method to check database for empID
//    private boolean checkDatabase(String empID) throws SQLException{
//        boolean isInDatabase = false;
//        int i = 0;
//        int inArray = 0;
//        String[] empIDs = getEmpIDFromDatabase();
//        
//        while(i < empIDs.length){
//            if(empID.equals(empIDs[i])){
//                 inArray++;
//            }
//            i++;
//        }
//        if(inArray != 0){
//            isInDatabase = true;
//              }
//        return isInDatabase;
//    }
//
//    private String[] getEmpIDFromDatabase() throws SQLException {
//        Set<String> arraySet = new HashSet<String>();
//        String[] arrayID;
//         Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/AUStudentEmployees",
//                                "kheneahm", "kennygoham");
//         
//         String query = "select EmployeeID from emp_info;";
//         
//         Statement stmnt = connect.createStatement();
//         
//         ResultSet rslt = stmnt.executeQuery(query);
//         
//         while(rslt.next()){
//             arraySet.add(rslt.getString("EmployeeID")); // grabs ID from database
//         }
//         
//         stmnt.close();
//         
//         //returns iterator from arraySet
//         Iterator<String> iterator = arraySet.iterator();
//         
//         //create an array with a fixed size
//         arrayID = new String[arraySet.size()];
//         int i = 0;
//         while(iterator.hasNext()){
//             arrayID[i] = iterator.next();
//             i++;
//        
//            }
//         return arrayID;
//    }
}
