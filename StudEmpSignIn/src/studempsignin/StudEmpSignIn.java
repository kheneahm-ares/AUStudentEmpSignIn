/*
 */
package studempsignin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


/**
 *
 * @author kheneahmares
 */
public class StudEmpSignIn {
        public static void main(String[] args) throws SQLException, ClassNotFoundException{
           
            
             //Load Driver one that is already given not one personally created
            //A driver is a concrete class that implements the driver interface
            //and must be loaded before connecting to a database
//            Class.forName("com.mysql.jdbc.Driver");
//            System.out.println("Driver Loaded");
            
            DBaseDriver connect = new DBaseDriver("jdbc:mysql://localhost:3306/AUStudentEmployees",
                    "kheneahm", "kennygoham");
            System.out.println("Database Connected!");
            
            
            connect.printTest();
    
        }
    
}
