package ConnetctDatabase;

import com.mysql.cj.MysqlConnection;
import org.example.Client;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ClientRequests {

    Connection connection;
    PreparedStatement ps;
    ResultSet rs;


    public void selectAllServices(){
        try{
            connection=ConnectDatabase.connection();
            String sql="SELECT * FROM carservice";
            ps=connection.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next()){
                int carServiceId=rs.getInt("carService_id");
                String carName=rs.getString("name");
                String location=rs.getString("location");
                String phone=rs.getString("phone");
                String email=rs.getString("email");
                System.out.println("CarId: "+carServiceId+" \nName: "+carName+" \nlocation: "+location+" \nphone: "+phone+" \nemail: "+email+"\n -------------------------------------");
            }
            System.out.println("Select products is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public void selectAllCar(Client client){
        try{
            connection=ConnectDatabase.connection();
            String sql="SELECT * FROM car WHERE user_id=?";
            ps=connection.prepareStatement(sql);
            ps.setInt(1,client.getId());
            rs=ps.executeQuery();
            while(rs.next()){
                int carId=rs.getInt("car_id");
                int userId=rs.getInt("user_id");
                String brand=rs.getString("brand");
                String model=rs.getString("model");
                int year=rs.getInt("year");
                String rgNumber=rs.getString("rg_number");
                System.out.println("CarId: "+carId+" \nuserId: "+userId+" \nbrand: "+brand+" \nmodel: "+model+" \nyear: "+year+"\nrgNumber: "+rgNumber+"\n" +" -------------------------------------");
            }
            System.out.println("Select products is successful");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }



    public void addCar(Client client, Scanner input){
        try{
            connection=ConnectDatabase.connection();
            String sql="INSERT INTO car (user_id,brand,model,year,rg_number) VALUES(?,?,?,?,?)";
            ps=connection.prepareStatement(sql);
            System.out.println("Enter brand: ");
            String brand=input.next();
            System.out.println("Enter model: ");
            String model=input.next();
            System.out.println("Enter year: ");
            int year=input.nextInt();
            System.out.println("Enter rg number: ");
            String rgNumber=input.next();
            ps.setInt(1,client.getId());
            ps.setString(2,brand);
            ps.setString(3,model);
            ps.setInt(4,year);
            ps.setString(5,rgNumber);
            ps.execute();
            System.out.println("Add car is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }





}
