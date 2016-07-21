/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studempsignin;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author kheneahmares
 */
public class EmployeeInfo {

    public EmployeeInfo() {

    }

    private final StringProperty EmployeeID = new SimpleStringProperty(this, "EmployeeID");
    private final StringProperty FirstName = new SimpleStringProperty(this, "FirstName");
    private final StringProperty LastName = new SimpleStringProperty(this, "LastName");
    private final StringProperty GroupName = new SimpleStringProperty(this, "GroupName");
    private final StringProperty HourlyWages = new SimpleStringProperty(this, "HourlyWage");
    private final StringProperty EstimatedHours = new SimpleStringProperty(this, "EstimatedHours");
    private final StringProperty EstimatedPay = new SimpleStringProperty(this, "EstimatedPay");
    private final BooleanProperty ClockedIn = new SimpleBooleanProperty(this, "ClockedIn");

    
    public StringProperty EmployeeIDProperty(){
        return EmployeeID;
    }
    public StringProperty FirstNameProperty(){
        return FirstName;
    }
    public StringProperty LastNameProperty(){
        return LastName;
    }
    public StringProperty GroupNameProperty(){
        return GroupName;
    }
    public StringProperty HourlyWagesProperty(){
        return HourlyWages;
    }
    public StringProperty EstimatedHoursProperty(){
        return EstimatedHours;
    }
    public StringProperty EstimatedPayProperty(){
        return EstimatedPay;      
    }
    public BooleanProperty ClockedInProperty(){
        return ClockedIn;
    }
    
    public final String getEmpID(){
        return EmployeeIDProperty().get();
    }
    public final String getFirstName(){
        return FirstNameProperty().get();
    }
    public final String getLastName(){
        return LastNameProperty().get();
    }
    public final String getGroupName(){
        return GroupNameProperty().get();
    }
    public final String getHourlyWage(){
        return HourlyWagesProperty().get();
    }
    public final String getEstimatedHours(){
        return EstimatedHoursProperty().get();
    }
    public final String getEstimatedPay(){
        return EstimatedPayProperty().get();
    }
    public final boolean getClockedIn(){
        return ClockedInProperty().get();
    }
    
    public final void setEmployeeID(String empID){
        EmployeeIDProperty().set(empID);
    }
    public final void setFirstNames(String fnames){
        FirstNameProperty().set(fnames);
    }
    public final void setLastNames(String lnames){
        LastNameProperty().set(lnames);
    }
    public final void setGroupNames(String groupNames){
        GroupNameProperty().set(groupNames);
    }
    public final void setHourlyWage(String hrlyWage){
        HourlyWagesProperty().set(hrlyWage);
    }
    public final void setHours(String estHours){
        EstimatedHoursProperty().set(estHours);
    }
    public final void setPay(String estPay){
        EstimatedPayProperty().set(estPay);
    }
    public final void setClockIn(boolean clockIn){
        ClockedInProperty().set(clockIn);
    }
    
    public EmployeeInfo(String empID, String FirstNames, String LastNames, String GroupNames,
                        String HourlyWage, String EstimatedHours, String EstimatedPay,
                        boolean ClockedIn){
        setEmployeeID(empID);
        setFirstNames(FirstNames);
        setLastNames(LastNames);
        setGroupNames(GroupNames);
        setHourlyWage(HourlyWage);
        setHours(EstimatedHours);
        setPay(EstimatedPay);
        setClockIn(ClockedIn);
    }
    
}
