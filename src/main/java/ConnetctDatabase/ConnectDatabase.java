package ConnetctDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDatabase {


    public static Connection connection(){
        String sqlUserName="root";
        String sqlPassword="Ivan0877449404";
        String urlDatabase="jdbc:mysql://localhost:3306/carservicedb";
        try{
            Connection connection= DriverManager.getConnection(urlDatabase,sqlUserName,sqlPassword);
            return connection;
        } catch (SQLException e) {
            System.out.println("It has a problem with database connection");
        }
        return null;
    }


}
