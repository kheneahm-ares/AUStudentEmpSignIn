 /*
 */
package studentempsignin;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author kheneahmares
 */
public class StudEmpSignIn extends Application {
    
    //fxml data fields
    private Stage primaryStage;
    private AnchorPane controlData;
    private BorderPane rootLayout;
    
    
        public static void main(String[] args) throws SQLException, ClassNotFoundException{
           
            
             //Load Driver one that is already given not one personally created
            //A driver is a concrete class that implements the driver interface
            //and must be loaded before connecting to a database
//            Class.forName("com.mysql.jdbc.Driver");
//            System.out.println("Driver Loaded");
            
            DBaseDriver connect = new DBaseDriver("jdbc:mysql://localhost:3306/AUStudentEmployees",
                    "kheneahm", "kennygoham");
            //System.out.println("Database Connected!");
            
            
            //connect.printTest();
            launch(args); //launch arguments to run fxml application
        }
        
        //must extends Application class to override
        @Override
        public void start(Stage primaryStage) throws Exception {
            this.primaryStage = primaryStage;
            this.primaryStage.setTitle("AU Student Employee Sign In");
            
            initializeRoot(); //initialize borderpane as root
            showControlsData(); //show fields from anchor pane
        }

    private void showControlsData() {
        FXMLLoader load = new FXMLLoader();
        load.setLocation(StudEmpSignIn.class.getResource("ShowControlGUI.fxml"));
        
        try {
            controlData = (AnchorPane)load.load();
        }
        catch(IOException exc){
            exc.printStackTrace();
        }
        
        rootLayout.setCenter(controlData);
    }

    private void initializeRoot() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StudEmpSignIn.class.getResource("RootGUI.fxml"));
        
        try{
            rootLayout = (BorderPane) loader.load();
        }
        catch(IOException exc){
            exc.printStackTrace();
    }
        Scene scn = new Scene(rootLayout);
        primaryStage.setScene(scn);
        primaryStage.show();
    
}
}
