package studentempsignin;

public class Employee {

    //data fields
    String empID;
    String fName;
    String lName;
    String groupName;
    String hourlyWage;
    String estHours;
    String estPay;
    String isClockedIn;
    
    public Employee(){
        //no arg cons
    }

    public Employee(String empID, String fName, String lName, String groupName, String hourlyWage, 
                    String estHours, String estPay, String isClockedIn){
        this.empID = empID;
        this.fName = fName;
        this.lName = lName;
        this.groupName = groupName;
        this.hourlyWage = hourlyWage;
        this.estHours = estHours;
        this.estPay = estPay;
        this.isClockedIn = isClockedIn;
        
    }
    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(String hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public String getEstHours() {
        return estHours;
    }

    public void setEstHours(String estHours) {
        this.estHours = estHours;
    }

    public String getEstPay() {
        return estPay;
    }

    public void setEstPay(String estPay) {
        this.estPay = estPay;
    }

    public String getIsClockedIn() {
        return isClockedIn;
    }

    public void setIsClockedIn(String isClockedIn) {
        this.isClockedIn = isClockedIn;
    }

}