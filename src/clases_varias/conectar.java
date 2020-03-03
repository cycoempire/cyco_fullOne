package clases_varias;
import java.sql.*;
import java.time.Clock;
import javax.swing.JOptionPane;


public class conectar {
    Connection con;
    
       public Connection conexion(){
       
           try {
               Class.forName("com.mysql.jdbc.Driver");
               //con = DriverManager.getConnection("jdbc:mysql://192.168.0.12:3306/cycodb?autoReconnect=true&useSSL=false","root","cebero1593");
              con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cycodb_fullone?autoReconnect=true&useSSL=false","root","cebero1593");
              //con = DriverManager.getConnection("jdbc:mysql://192.168.0.15:3306/db_empleados","root","cebero1593");
              //con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cycodb","root","cebero1593");
               //System.out.println("conexion establecida");
               //JOptionPane.showMessageDialog(null, "Conexion establecida");
           } catch (ClassNotFoundException | SQLException e) {
            //System.out.println("no se pudo conectar");
            JOptionPane.showMessageDialog(null, "No se puedo establecer la conexion con la base de datos: "+e);
           }
       return con;
       }
}
