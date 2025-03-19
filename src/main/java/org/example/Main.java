package org.example;
import java.sql.*;

import ConnetctDatabase.ConnectDatabase;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection connection=ConnectDatabase.connection();
        System.out.println(connection);

        String sql="SELECT * FROM carservicedb.carservice ";
        PreparedStatement ps=connection.prepareStatement(sql);
        ResultSet rs=ps.executeQuery();
        while(rs.next()){
            int id=rs.getInt("carService_id");
            String name=rs.getString("name");
            System.out.println(id+" "+name);
        }

    }
}