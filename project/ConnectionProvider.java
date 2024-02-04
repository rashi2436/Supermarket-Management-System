package project;
import java.sql.*;

public class ConnectionProvider {
    public static Connection getCon()
            
    {
        
        
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con;  
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/supermarket","root","9824524429");
           return con;
           }
                   
        catch(ClassNotFoundException | SQLException e)
        {
        return null;
        }

    }
}

  
