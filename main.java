package mysqlproject;

import java.sql.*;
import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        try {
            int choice = 0;
            Employee emp = new Employee();
            do {
                System.out.println("1.Create Values For The Database");
                System.out.println("2.Read Values For The Database");
                System.out.println("3.Update Values For The Database");
                System.out.println("4.Delete Values For The Database");
                System.out.println("5.Exit The Database");
                System.out.print("Enter Your Choice : ");
                Scanner input = new Scanner(System.in);
                choice = input.nextInt();
                switch (choice) {
                    case 1:
                        emp.getEmployeeDetails();
                        emp.createEmployeeDetails();
                        break;
                    case 2:
                        emp.readEmployeeDetails();
                        break;
                    case 3:
                        emp.updateEmployeeDetails();
                        break;
                    case 4:
                        emp.deleteEmployeeDetails();
                        break;
                    case 5:
                        System.out.println("Exiting The Database..");
                        break;
                    default:
                        System.out.println("Please Select The Correct Option To Use The Database...");
                        break;
                }
            } while (choice != 5);
            System.out.println("Thanks For Using The Database..");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

class Employee {

    private String name;
    private String password;
    private int id;
    private String blood_group;
    private int age;
    private int salary;

    public void getEmployeeDetails() {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter Employee Name : ");
        name = input.nextLine();
        System.out.println("Enter Employee Password : ");
        password = input.nextLine();
        System.out.println("Enter Employee ID : ");
        id = input.nextInt();
        System.out.println("Enter Employee Blood Group : ");
        blood_group = input.nextLine();
        System.out.println("Enter Employee Age : ");
        age = input.nextInt();
        System.out.println("Enter Employee Salary : ");
        salary = input.nextInt();
    }

    public void createEmployeeDetails() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql://localhost/employee?", "root", "");
        Connection con = dbmsconnect.getConnection();
        String sql = "insert into employee values (?,?,?,?,?,?);";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, password);
        stmt.setInt(3, id);
        stmt.setString(4, blood_group);
        stmt.setInt(5, age);
        stmt.setInt(6, salary);
        stmt.execute();
        System.out.println("Record Inserted Successfully");
        System.out.println("");
        dbmsconnect.closeConnection(con, stmt);
    }

    public void readEmployeeDetails() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        try {
            dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql://localhost/employee?", "root", "");
            Connection con = dbmsconnect.getConnection();
            System.out.println("Enter Employee Name For Read The Record : ");
            Scanner input = new Scanner(System.in);
            String name = input.nextLine();
            String sql = "select * from employee where name=?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() == false) {
                System.out.println("There is No Such Record Found in The Database");
            } else {
                while (rs.next()) {

                    System.out.println("Employee Name = " + rs.getString(1));
                    System.out.println("Employee Password = " + rs.getString(2));
                    System.out.println("Employee ID = " + rs.getInt(3));
                    System.out.println("Employee Blood_Group = " + rs.getString(4));
                    System.out.println("Employee Age = " + rs.getInt(5));
                    System.out.println("Employee Salary = " + rs.getInt(6));
                }
            }
            dbmsconnect.closeConnection(con, stmt);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateEmployeeDetails() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        try {
            dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql://localhost/employee?", "root", "");
            Connection con = dbmsconnect.getConnection();
            System.out.println("Enter Employee Name : ");
            Scanner input = new Scanner(System.in);
            String name = input.nextLine();
            System.out.println("Enter Employee New Password : ");
            String password = input.nextLine();
            String sql = "update employee set password = ? where name = ?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, password);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Record Updated Successfully..");
            } else {
                System.out.println("There is No such Record Found in the Database...");
            }
            dbmsconnect.closeConnection(con, stmt);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteEmployeeDetails() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        try {
            dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql://localhost/employee?", "root", "");
            Connection con = dbmsconnect.getConnection();
            System.out.println("Enter Employee Name For Deleting The Record : ");
            Scanner input = new Scanner(System.in);
            String name = input.nextLine();
            String sql = "delete from employee where name=?;";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Delete Record Successfully..");
            } else {
                System.out.println("There is No such Record Found in the Database...");
            }
            dbmsconnect.closeConnection(con, stmt);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}

class dbmsconnection {

    String url;
    String username;
    String password;

    public dbmsconnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        Connection con = null;
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        con = DriverManager.getConnection(url, username, password);
        System.out.println("XAMPP MySQL Server is Connected...");
        System.out.println("Employee Database Programming Menu");
        return con;
    }

    public void closeConnection(Connection con, Statement stmt) throws SQLException {
        con.close();
        stmt.close();
    }
}
