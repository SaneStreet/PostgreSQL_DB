import java.io.*;
import java.sql.*;

public class DB_Statements {

    //  Declare a Statement
    private static Statement stmt = null;

    //  Declare & create a connection
    private static Connection con = DB_Connector.connect();

    //  Declare a result set
    private static ResultSet rs = null;

    //  Declare a PreparedStatement
    private static PreparedStatement pst = null;

    //Method for inserting Data to Database Table
    public void insertData(){
        Employee employee = new Employee(41, "Bubba", 5000.0);
        String query1 = "insert into employees (emp) values(?)";
        String query2 = "select * from employees";

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(employee);

            byte[] employeeAsByte = baos.toByteArray();

            pst = con.prepareStatement(query1);

            ByteArrayInputStream bais = new ByteArrayInputStream(employeeAsByte);

            pst.setBinaryStream(1, bais, employeeAsByte.length);

            pst.executeUpdate();
            System.out.println("\n--Query 1 executed--");
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.out.println("\n--Query 1 did not execute--");
        }

        try {
            stmt = con.createStatement();
            //ResultSet for our Select query2
            rs = stmt.executeQuery(query2);

            while(rs.next()){
                byte[] st = (byte[]) rs.getObject(2);
                ByteArrayInputStream baip = new ByteArrayInputStream(st);
                ObjectInputStream ois = new ObjectInputStream(baip);
                Employee emp = (Employee) ois.readObject();
                System.out.println(emp.toString());
            }

            System.out.println("\n--Retrieve executed--");
        }
        catch (Exception ex){
            ex.printStackTrace();
            System.out.println("\n--Retrieve did not execute--");

        }
    }
}
