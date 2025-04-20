package ConnetctDatabase;

import InterfacePackage.ClientActions;
import com.mysql.cj.MysqlConnection;
import org.example.Client;
import org.example.Request;
import org.example.Service;
import packageEnum.Status;

import java.io.IOException;
import java.io.StringReader;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
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
            String sql="SELECT * FROM services WHERE services_id=?";
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
            String sql="SELECT * FROM services";
            ps=connection.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next()){
                int servicesId=rs.getInt("services_id");
                String name=rs.getString("name");
                String description=rs.getString("description");
                Double price=rs.getDouble("price");
                System.out.println("servicesId: "+servicesId+" \nName: "+name+" \ndescription: "+description+" \nprice: "+price+"\n -------------------------------------");
            }
            System.out.println("Select products is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    @Override
    public boolean addCar(Client client,String brand, String model,int year, String rgNumber) {
        boolean flag= false;
        try{
            connection=ConnectDatabase.connection();
            String sql="INSERT INTO car (user_id,brand,model,year,rg_number) VALUES(?,?,?,?,?)";
            ps=connection.prepareStatement(sql);
            ps.setInt(1,client.getId());
            ps.setString(2,brand);
            ps.setString(3,model);
            ps.setInt(4,year);
            ps.setString(5,rgNumber);
            ps.execute();
            flag=true;
            if(flag){
                System.out.println("Add car is successful");   
            }else{
                System.out.println("Error with add car");
            }
        }catch (Exception e){
            System.out.println("Error with add car");
        }
        return flag;
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


    //addRequest, addRequestServices,getRequestFromDatabase,addAllServicesFromRequest вървят заедно
    //Главния викащ се от потребителя метод е addRequest другите са спомагателни
    //след като се извика addRequest се създава заявка от потребителя и данните се въвеждат в таблица request
    //след това с няколко милисекунди закъснение се връща обект заявка по дадения текущ час на създаване
    //този върна обект се подава на метод addAllServicesFromRequest, който от своя страна извиква метода addRequestServices и въвежда всички услуги за дадената заявка в междинната таблица
    //Създава се request->взима се този request->записва се списък с услуги->обхожда се списъка и се запълва междинната таблица


    @Override
    public boolean addRequest(Client client) {
        try{
            boolean flag=false;
            connection=ConnectDatabase.connection();
            viewCar(client);
            String sql="INSERT INTO request(car_id,user_id) VALUES (?,?)";
            ps=connection.prepareStatement(sql);
            //Select car
            System.out.println("Choose a car: ");
            int carId= input.nextInt();
            ps.setInt(1,carId);
            System.out.println("Step 1");
            System.out.println(client.getId());
            ps.setInt(2,client.getId());
            System.out.println("Step 2");
            ps.execute();
            System.out.println("Step 3");
            ////////////////////////////////////////////////////////
            Request request=getRequestFromDatabase();
            request.addServices(client,input);
            addAllServicesFromRequest(client,request);
            flag=true;
            if(flag){
                System.out.println("Request is created successfully");
            }else{
                System.out.println("Error with add request");
            }
            return true;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }





    @Override
    public void addRequestServices(Client client,Request request,Service service) {
        try{
           connection=ConnectDatabase.connection();
           String sql="INSERT INTO requestServices(request_id,services_id) VALUES(?,?)";
           ps=connection.prepareStatement(sql);
           ps.setInt(1,request.getRequestId());
           ps.setInt(2,service.getId());
           ps.execute();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



    //взимаме заявката която създадохме в дадения момент и връщаме обект от Request за да му добавим после списък с услугите и да извикаме другите два метод addRequestServices,addAllServicesFromRequest
    public Request getRequestFromDatabase(){
        try{
            connection=ConnectDatabase.connection();
            String sql="SELECT * FROM request ORDER BY createdOn DESC LIMIT 1";
            ps=connection.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                int requestId=rs.getInt("request_id");
                int carId=rs.getInt("car_id");
                int userId=rs.getInt("user_id");
                Status status= Status.valueOf(rs.getString("status").toUpperCase());
                Timestamp createdOn=rs.getTimestamp("createdOn");
                Timestamp completedOn=rs.getTimestamp("completedOn");
                boolean modifiedRequest=rs.getBoolean("modified_request");
                Request request=new Request(requestId,carId,userId,status,createdOn,completedOn,modifiedRequest);
                return request;
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }


    //добавяме всички записи за дадената заявка с всички нейни услуги в междинната таблица requestServices
    public void addAllServicesFromRequest(Client client,Request request){
        ArrayList<Service> services=request.getListOfServices();
        for (Service service: services) {
            addRequestServices(client,request,service);
        }
    }

    @Override
    public boolean viewRequestHistory(Client client) {
        boolean flag=false;
        try{
            connection=ConnectDatabase.connection();
            String sql="SELECT * FROM request WHERE user_id=?";
            ps=connection.prepareStatement(sql);
            ps.setInt(1,client.getId());
            rs=ps.executeQuery();
            if(rs!=null) {
                flag=true;
                while (rs.next()) {
                    int requestId = rs.getInt("request_id");
                    int carId = rs.getInt("car_id");
                    int userId = rs.getInt("user_id");
                    Date completedOn = rs.getDate("completedOn");
                    Date createdOn = rs.getDate("createdOn");
                    boolean modifiedRequest = rs.getBoolean("modified_request");
                    String status = rs.getString("status");
                    System.out.println("Request Id: " + requestId
                            + "\nCar Id: " + carId
                            + "\nUser Id: " + userId
                            + "\nCompleted On: " + completedOn
                            + "\nCreated On: " + createdOn
                            + "\nModified request: " + modifiedRequest
                            + "\nStatus: " + status);
                }
            }
            if(flag){
                System.out.println("Select requests is successful");
                return true;
            }else{
                System.out.println("Select requests is not successful");
                return false;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }

    }


    //модифицирането на заявката ще бъде направено по телефон.Тоест когато клиент иска да промени или изтрие заявката то той променя полето modifierRequest на true
    //и тогава наш служител ще се свърже с него за да го попита обстойно какъв е проблема и да го разубеди тъй като така губим клиенти
    @Override
    public boolean requestModification(Client client,int requestId) {
        try{
            boolean flag=false;
            connection=ConnectDatabase.connection();
            String sql="UPDATE request SET modified_request=? WHERE request_id=?";
            ps=connection.prepareStatement(sql);
            ps.setBoolean(1,true);
            ps.setInt(2,requestId);
            ps.execute();
            flag=true;
            if(flag){
                System.out.println("Modified request is successful");
                System.out.println("Wait for a call");
            }else{
                System.out.println("Error with requestModify");
            }
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
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
