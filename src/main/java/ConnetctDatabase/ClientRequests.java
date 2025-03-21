package ConnetctDatabase;

import InterfacePackage.ClientActions;
import com.mysql.cj.MysqlConnection;
import org.example.Client;
import org.example.Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class ClientRequests implements ClientActions {

    Scanner input=new Scanner(System.in);
    Connection connection;
    PreparedStatement ps;
    ResultSet rs;


    //взимаме всички услуги които клиента иска за неговия ввтомобил,връщаме ги като обекти и ги записваме в списъка на дадената поръчка откъдето после ще запълним и междинните таблици
    @Override
    public Service getService(Client client,int serviceId) {
        try{
            connection=ConnectDatabase.connection();
            String sql="SELECT FROM services WHERE services_id=?";
            ps=connection.prepareStatement(sql);
            ps.setInt(1,serviceId);
            rs=ps.executeQuery();
            while(rs.next()){
                int id=rs.getInt("services_id");
                String name=rs.getString("name");
                String description=rs.getString("description");
                double price=rs.getDouble("price");
                Service service=new Service(id,name,description,price);
                return service;
            }
            System.out.println("Service is getting successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public void viewServices(Client client) {
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



    @Override
    public void addCar(Client client) {
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

    @Override
    public void viewCar(Client client) {
        boolean flag=false;
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
                flag=true;
            }
            //даваме му флаг тук защото дори и да не съществува user с даденото ни id то не хвърля грешка а просто не извежда коли
            if (flag){
                System.out.println("Select products is successful");
            }else{
                System.out.println("No products for view");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }


    //da zapylnq i mevdinnata tablica
    @Override
    public void addRequest(Client client) {
        try{
            connection=ConnectDatabase.connection();
            String sql="INSERT INTO request(car_id,user_id,) VALUES (?,?)";
            ps=connection.prepareStatement(sql);
            //Select car
            System.out.println("Choose a car: ");
            viewCar(client);
            int car= input.nextInt();
            ps.setInt(1,car);
            ps.setInt(2,client.getId());
            System.out.println("Request is created successfully");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void viewRequestHistory(Client client) {
        boolean flag=false;
        try{
            connection=ConnectDatabase.connection();
            String sql="SELECT * FROM request WHERE user_id=?";
            ps=connection.prepareStatement(sql);
            ps.setInt(1,client.getId());
            rs=ps.executeQuery();
            while(rs.next()){
                int requestId=rs.getInt("request_id");
                int carId=rs.getInt("car_id");
                int userId=rs.getInt("user_id");
                Date completedOn=rs.getDate("completedOn");
                Date createdOn=rs.getDate("createdOn");
                boolean modifiedRequest=rs.getBoolean("modified_request");
                String status=rs.getString("status");
                System.out.println("Request Id: "+requestId
                        + "\nCar Id: "+carId
                        +"\nUser Id: "+userId
                        +"\nCompleted On: "+completedOn
                        +"\nCreated On: "+createdOn
                        +"\nModified request: "+modifiedRequest
                        + "\nStatus: "+status);
            }
            if(flag){
                System.out.println("Select requests is successful");
            }else{
                System.out.println("Select requests is not successful");
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    //модифицирането на заявката ще бъде направено по телефон.Тоест когато клиент иска да промени или изтрие заявката то той променя полето modifierRequest на true
    //и тогава наш служител ще се свърже с него за да го попита обстойно какъв е проблема и да го разубеди тъй като така губим клиенти
    @Override
    public void requestModification(Client client) {
        try{
            connection=ConnectDatabase.connection();
            String sql="UPDATE request SET modified_request=?";
            ps=connection.prepareStatement(sql);
            ps.setBoolean(1,true);
            ps.execute();
            System.out.println("Modified request is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateUserData(Client client) {
        try{
            connection=ConnectDatabase.connection();
            String sql="UPDATE user SET password=? WHERE user_id=?";
            ps=connection.prepareStatement(sql);
            System.out.println("Enter new password: ");
            String password=input.next();
            if(checkPassword(password)){
                ps.setString(1,password);
            }
            ps.setInt(2,client.getId());
            ps.execute();
            System.out.println("Update data for user is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    @Override
    public boolean checkPassword(String password) {
        String regex="^.{6,}$";
        if(password.matches(regex)){
            System.out.println("Password is correct");
            return true;
        }
        System.out.println("Password is incorrect");
        System.out.println("You must enter minimum 6 symbols");
        return false;
    }
}
