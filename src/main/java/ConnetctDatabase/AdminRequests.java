package ConnetctDatabase;

import InterfacePackage.AdminActions;
import org.example.Admin;
import org.example.Service;
import packageEnum.Status;
import packageEnum.UserType;

import java.net.ConnectException;
import java.sql.*;
import java.util.Scanner;

public class AdminRequests implements AdminActions {

    Scanner input=new Scanner(System.in);
    PreparedStatement ps;
    ResultSet rs;
    Connection connection;

    @Override
    public void addServices(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="INSERT INTO services(name,description,price) VALUES(?,?,?)";
            ps=connection.prepareStatement(sql);
            System.out.println("Enter service's name: ");
            String name=input.next();
            System.out.println("Enter description: ");
            String description=input.next();
            System.out.println("Enter price: ");
            double price= input.nextDouble();
            ps.setString(1,name);
            ps.setString(2,description);
            ps.setDouble(3,price);
            ps.execute();
            System.out.println("Add service is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteServices(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="DELETE * FROM services WHERE services_id=?";
            ps=connection.prepareStatement(sql);
            viewAllServices(admin);
            System.out.println("Enter service id: ");
            int serviceId=input.nextInt();
            ps.setInt(1,serviceId);
            ps.execute();
            System.out.println("Delete service is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editServices(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="UPDATE services SET price=? WHERE services_id=?";
            ps= connection.prepareStatement(sql);
            viewAllServices(admin);
            System.out.println("Enter serviceId: ");
            int serviceId= input.nextInt();
            System.out.println("Enter new price: ");
            double price= input.nextDouble();
            ps.setDouble(1,price);
            ps.setInt(2,serviceId);
            ps.execute();
            System.out.println("Update service is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addUser(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="INSERT INTO user(name,email,password,phone,address,role) VALUES(?,?,?,?,?,?)";
            ps=connection.prepareStatement(sql);
            System.out.println("Enter name: ");
            String name= input.next();
            String email;
            String password;
            String phone;
            do{
                System.out.println("Enter email: ");
                email= input.next();
            }while(!checkEmail(email));
            do{
                System.out.println("Enter password: ");
                password= input.next();
            }while(!checkPassword(password));
            do{
                System.out.println("Enter phone: ");
                phone= input.next();
            }while(!checkPhone(phone));
            ps.setString(1,name);
            ps.setString(2,email);
            ps.setString(3,password);
            ps.setString(4,phone);
            System.out.println("Enter address: ");
            String address=input.next();
            UserType userType=getUserType();
            ps.setString(5,address);
            ps.setString(6,userType.toString());
            ps.execute();
            System.out.println("Add user is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public UserType getUserType(){
        System.out.println("Enter type role:\n1-Client\n2-Admin");
        int choice= input.nextInt();
        if(choice==1){
            return UserType.CLIENT;
        } else if (choice==2) {
            return UserType.CLIENT;
        }else{
            //по подразбиране винаги е тип клиент така че ако на напише един от двата дирекно си става на клиент
            return UserType.CLIENT;
        }
    }

    //може да добавим метод който по зададено userId да връща обект user и да проверим неговата роля, за да се трие само client.
    @Override
    public void deleteUser(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="DELETE FROM user WHERE user_id=?";
            ps=connection.prepareStatement(sql);
            System.out.println("ENter user id: ");
            int userId= input.nextInt();
            ps.setInt(1,userId);
            ps.execute();
            System.out.println("Delete user is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editUser(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="UPDATE user SET password=? WHERE user_id=?";
            ps=connection.prepareStatement(sql);
            System.out.println("Enter userId: ");
            int userId= input.nextInt();
            String password;
            do{
                System.out.println("Enter new password: ");
                password=input.next();
            }while(!checkPassword(password));
            ps.setString(1,password);
            ps.setInt(2,userId);
            ps.execute();
            System.out.println("Edit user is successful");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addCarService(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="INSERT INTO carService(name,location,email,phone) VALUES(?,?,?,?)";
            ps=connection.prepareStatement(sql);
            System.out.println("Enter name: ");
            String name=input.next();
            System.out.println("Enter location: ");
            String location=input.next();
            String email;
            String phone;
            do{
                System.out.println("Enter email: ");
                email= input.next();
            }while(!checkEmail(email));
            do{
                System.out.println("Enter phone: ");
                phone= input.next();
            }while(!checkPhone(phone));
            ps.setString(1,name);
            ps.setString(2,location);
            ps.setString(3,email);
            ps.setString(4,phone);
            ps.execute();
            System.out.println("Add carService is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteCarService(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="DELETE * FROM carService WHERE carService_id=?";
            ps=connection.prepareStatement(sql);
            viewAllCarServices(admin);
            System.out.println("Choose car service: ");
            int carServiceId= input.nextInt();
            ps.setInt(1,carServiceId);
            ps.execute();
            System.out.println("Delete car service is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void editCarService(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="UPDATE carService SET phone=? WHERE carService_id=?";
            ps=connection.prepareStatement(sql);
            viewAllCarServices(admin);
            System.out.println("Choose car service: ");
            int carServiceId= input.nextInt();
            String phone;
            do{
                System.out.println("Enter new phone: ");
                phone=input.next();
            }while(!checkPhone(phone));
            ps.setString(1,phone);
            ps.setInt(2,carServiceId);
            ps.execute();
            System.out.println("Update car service is successful ");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteRequest(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="DELETE FROM request WHERE request_id=?";
            ps= connection.prepareStatement(sql);
            viewAllRequest(admin);
            System.out.println("Choose request: ");
            int requestId= input.nextInt();
            ps.execute();
            System.out.println("Delete request is successful");
            //тук се извиква след това директно и deleteRequestService тъй като тя е междинна таблица и щом трием request я трием отсякъде
            deleteRequestService(admin,requestId);
            System.out.println("All delete request methods work correct");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifyRequest(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="UPDATE request SET status=? WHERE request_id=?";
            ps= connection.prepareStatement(sql);
            viewAllRequest(admin);
            System.out.println("Choose request: ");
            int requestId= input.nextInt();
            System.out.println("Enter new status for request: "+"\n1- "+Status.APPROVED+"\n2- "+Status.COMPLETED+"\n3- "+Status.REJECTED);
            int choice= input.nextInt();
            if (choice==1){
                ps.setString(1,Status.APPROVED.toString());
            } else if (choice==2){
                ps.setString(1,Status.COMPLETED.toString());
            } else if (choice==3) {
                ps.setString(1,Status.REJECTED.toString());
            }else{
                ps.setString(1,Status.PENDING.toString());
            }
            ps.setInt(2,requestId);
            ps.execute();
            System.out.println("Update request is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteRequestService(Admin admin,int requestId) {
        try{
            connection=ConnectDatabase.connection();
            String sql="DELETE * FROM requestServices WHERE request_id=?";
            ps= connection.prepareStatement(sql);
            ps.setInt(1,requestId);
            ps.execute();
            System.out.println("Delete from requestService is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addCar(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="INSERT INTO car (user_id,brand,model,year,rg_number) VALUES(?,?,?,?,?)";
            ps=connection.prepareStatement(sql);
            System.out.println("Enter client id: ");
            int clientId=input.nextInt();
            System.out.println("Enter brand: ");
            String brand=input.next();
            System.out.println("Enter model: ");
            String model=input.next();
            System.out.println("Enter year: ");
            int year=input.nextInt();
            System.out.println("Enter rg number: ");
            String rgNumber=input.next();
            ps.setInt(1,clientId);
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
    public void deleteCar(Admin admin) {
        try{
            connection= ConnectDatabase.connection();
            String sql="DELETE FROM car WHERE car_id=?";
            ps=connection.prepareStatement(sql);
            viewAllCar(admin);
            System.out.println("Choose car: ");
            int carId= input.nextInt();
            ps.setInt(1,carId);
            ps.execute();
            System.out.println("Delete car is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void editCar(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="UPDATE car SET rg_number=? WHERE car_id=?";
            ps=connection.prepareStatement(sql);
            viewAllCar(admin);
            System.out.println("Choose car: ");
            int carId= input.nextInt();
            System.out.println("Enter new rgNumber: ");
            String rgNumber=input.next();
            ps.setString(1,rgNumber);
            ps.setInt(2,carId);
            ps.execute();
            System.out.println("Update is successful");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean checkPhone(String phone) {
        String regex="[0-9]{10}";
        if(phone.matches(regex)){
            System.out.println("Phone is correct");
            return true;
        }
        System.out.println("Phone is incorrect");
        return false;
    }

    @Override
    public boolean checkEmail(String email) {
        String regex="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if(email.matches(regex)){
            System.out.println("Email is correct");
            return true;
        }
        System.out.println("Email is incorrect");
        return  false;
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

    @Override
    public void viewAllServices(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="SELECT * FROM services";
            ps=connection.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next()){
                int servicesId=rs.getInt("services_id");
                String name=rs.getString("name");
                String description=rs.getString("description");
                double price=rs.getDouble("price");
                System.out.println("serviceId: "+servicesId+"\nname: "+name+" \ndescription: "+description+" \nprice: "+price);
            }
            System.out.println("Select products is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void viewAllCar(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="SELECT * FROM car";
            ps=connection.prepareStatement(sql);
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
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void viewAllCarServices(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="SELECT * FROM carService";
            ps= connection.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                int carServiceId=rs.getInt("carService_id");
                String name=rs.getString("name");
                String location=rs.getString("location");
                String email=rs.getString("email");
                String phone=rs.getString("phone");
                System.out.println("Id: "+carServiceId+"\nname: "+name+"\nlocation: "+location+"\nemail: "+email+"\nphone: "+phone);
                System.out.println("-------------------------------------");
            }
            System.out.println("Select all car services is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void viewAllRequest(Admin admin) {
        try{
            connection=ConnectDatabase.connection();
            String sql="SELECT * FROM request";
            ps= connection.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next()){
                int requestId=rs.getInt("request_id");
                int carId=rs.getInt("car_id");
                int userId=rs.getInt("user_id");
                Status status= Status.valueOf(rs.getString("status"));
                Timestamp createdOn=rs.getTimestamp("createdOn");
                Timestamp completed=rs.getTimestamp("completedOn");
                boolean modifyRequest=rs.getBoolean("modified_request");
                System.out.println("Id: "+requestId+"\ncarId: "+carId+"\nuserId: "+userId+"\nstatus: "+status+"\ncreatedOn: "+createdOn+"\ncompletedOn: "+completed+"\nmodifyRequest: "+modifyRequest);
            }
            System.out.println("Select all request is successful");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
