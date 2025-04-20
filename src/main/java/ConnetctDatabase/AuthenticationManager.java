package ConnetctDatabase;

import InterfacePackage.AuthenticationManagerI;
import org.example.Admin;
import org.example.CarSystem;
import org.example.Client;
import org.example.User;
import packageEnum.UserType;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class AuthenticationManager implements AuthenticationManagerI {


    ArrayList<User> listOfUsers=new ArrayList<>();
    Scanner input=new Scanner(System.in);
    PreparedStatement ps;
    ResultSet rs;
    Connection connection;

    public ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    public void setListOfUsers(ArrayList<User> listOfUsers) {
        this.listOfUsers = listOfUsers;
    }


    public void mainMenu(){
        System.out.println("1-Register\n2-LogIn");
        int choice=input.nextInt();
        switch (choice){
            case 1:
                if(registerForm()){
                    logIn();
                }else{
                    mainMenu();
                }
                break;
            case 2:
                if (!logIn()){
                    mainMenu();
                }
                break;
        }
        //ако не влезе в нито единия от case-овете значи се извиква пак main manu-то
        System.out.println("Wrong choice!");
        mainMenu();
    }

    /*
    @Override
    public boolean logIn() {
        try{
            connection=ConnectDatabase.connection();
            String sql="SELECT * FROM user WHERE email=? AND password=?";
            ps=connection.prepareStatement(sql);
            System.out.println("Enter email: ");
            String email=input.next();
            System.out.println("Enter password: ");
            String password=input.next();
            ps.setString(1,email);
            ps.setString(2,password);
            rs= ps.executeQuery();
            while(rs.next()){
                if(rs.getString("role").equals("client")){
                    Client client=new Client(rs.getInt("user_id"),rs.getString("name"), rs.getString("email"),rs.getString("password"),rs.getString("phone"),rs.getString("address"));
                    listOfUsers.add(client);
                    clientMenu(client);
                    return true;
                }else if(rs.getString("role").equals("admin")){
                    Admin admin=new Admin(rs.getInt("user_id"),rs.getString("name"), rs.getString("email"),rs.getString("password"),rs.getString("phone"),rs.getString("address"));
                    listOfUsers.add(admin);
                    adminMenu(admin);
                    return true;
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }

     */

    @Override
    public boolean logIn(){
        int attemptCounter = 0;

        while (attemptCounter < 3) {
            try {
                connection = ConnectDatabase.connection();
                String sql = "SELECT * FROM user WHERE email=? AND password=?";
                ps = connection.prepareStatement(sql);

                System.out.println("Enter email: ");
                String email = input.next();
                System.out.println("Enter password: ");
                String password = input.next();

                ps.setString(1, email);
                ps.setString(2, password);
                rs = ps.executeQuery();

                if (rs.next()) {
                    String role = rs.getString("role");

                    if (role.equals("client")) {
                        Client client = new Client(
                                rs.getInt("user_id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("phone"),
                                rs.getString("address")
                        );
                        listOfUsers.add(client);
                        clientMenu(client);
                        return true;
                    } else if (role.equals("admin")) {
                        Admin admin = new Admin(
                                rs.getInt("user_id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("phone"),
                                rs.getString("address")
                        );
                        listOfUsers.add(admin);
                        adminMenu(admin);
                        return true;
                    }
                } else {
                    System.out.println("Invalid email or password. Try again.");
                    attemptCounter++;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                return false;
            }
        }

        System.out.println("3 failed login attempts reached.");
        throw new RuntimeException("Maximum login attempts exceeded. Access denied.");
    }

    @Override
    public boolean registerForm() {
        try{
            connection=ConnectDatabase.connection();
            String sql="INSERT INTO user(name,email,password,phone,address) VALUES(?,?,?,?,?)";
            ps=connection.prepareStatement(sql);
            System.out.println("Enter name: ");
            String name=input.next();
            ps.setString(1,name);
            String email;
            String password;
            String phone;
            do{
                System.out.println("Enter email: ");
                email=input.next();
            }while(!checkEmail(email));
            ps.setString(2,email);

            do{
                System.out.println("Enter password: ");
                password=input.next();
            }while(!checkPassword(password));
            ps.setString(3,password);

            do{
                System.out.println("Enter phone: ");
                phone=input.next();
            }while(!checkPhone(phone));
            ps.setString(4,phone);

            System.out.println("Enter address: ");
            String address=input.next();
            ps.setString(5,address);
            ps.execute();
            System.out.println("Register is successful");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }



    public void clientMenu(Client client){
        ClientRequests clientRequests=new ClientRequests();
        System.out.println("Enter option:\n" +
                "1-viewServices\n" +
                "2-viewCar\n" +
                "3-viewRequestHistory\n" +
                "4-addCar\n" +
                "5-addRequest\n" +
                "6-requestModification\n" +
                "7-updateUserData\n" +
                "8-Exit");
        int choice=input.nextInt();
        switch (choice){
            case 1:
                clientRequests.viewServices(client);
                clientMenu(client);
                break;
            case 2:
                clientRequests.viewCar(client);
                clientMenu(client);
                break;
            case 3:
                clientRequests.viewRequestHistory(client);
                clientMenu(client);
                break;
            case 4:
                System.out.println("Enter brand: ");
                String brand=input.next();
                System.out.println("Enter model: ");
                String model=input.next();
                System.out.println("Enter year: ");
                int year=input.nextInt();
                System.out.println("Enter rg number: ");
                String rgNumber=input.next();
                clientRequests.addCar(client,brand,model,year,rgNumber);
                clientMenu(client);
                break;
            case 5:
                clientRequests.addRequest(client);
                clientMenu(client);
                break;
            case 6:
                System.out.println("Enter request Id: ");
                int requestId=input.nextInt();
                clientRequests.requestModification(client,requestId);
                clientMenu(client);
                break;
            case 7:
                clientRequests.updateUserData(client);
                clientMenu(client);
                break;
            case 8:
                mainMenu();
                break;
        }

    }



    public void adminMenu(Admin admin) throws SQLException {
        AdminRequests adminRequest=new AdminRequests();
        System.out.println("Enter option:\n" +
                "1-addServices\n" +
                "2-addUser\n" +
                "3-addCarService\n" +
                "4-editServices\n" +
                "5-editUser\n" +
                "6-editCarService\n" +
                "7-editCar\n" +
                "8-modifyRequest\n" +
                "9-deleteServices\n" +
                "10-deleteUser\n" +
                "11-deleteCarService\n" +
                "12-deleteRequest\n" +
                "13-deleteCar\n" +
                "14-Exit");
        int choice=input.nextInt();
        switch (choice){
            case 1:
                adminRequest.addServices(admin);
                adminMenu(admin);
                break;
            case 2:
                adminRequest.addUser(admin);
                adminMenu(admin);
                break;
            case 3:
                adminRequest.addCarService(admin);
                adminMenu(admin);
                break;
            case 4:
                adminRequest.editServices(admin);
                adminMenu(admin);
                break;
            case 5:
                adminRequest.editUser(admin);
                adminMenu(admin);
                break;
            case 6:
                adminRequest.editCarService(admin);
                adminMenu(admin);
                break;
            case 7:
                adminRequest.editCar(admin);
                adminMenu(admin);
                break;
            case 8:
                adminRequest.modifyRequest(admin);
                adminMenu(admin);
                break;
            case 9:
                adminRequest.deleteServices(admin);
                adminMenu(admin);
                break;
            case 10:
                adminRequest.deleteUser(admin);
                adminMenu(admin);
                break;
            case 11:
                adminRequest.deleteCarService(admin);
                adminMenu(admin);
                break;
            case 12:
                adminRequest.deleteRequest(admin);
                adminMenu(admin);
                break;
            case 13:
                adminRequest.deleteCar(admin);
                adminMenu(admin);
                break;
            case 14:
                mainMenu();
                break;
        }
        mainMenu();
    }



/*
    public boolean checkEmail(String email){
        String regex="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if(email.matches(regex)){
            System.out.println("Email is correct");
            return true;
        }
        System.out.println("Email is incorrect");
        return  false;
    }
*/
public boolean checkEmail(String email) {
    if (email == null || !email.matches("^[\\w.-]+@[\\w-]+\\.[a-zA-Z]{2,}$")) {
        throw new IllegalArgumentException("Invalid email");
    }
    return true;
}

    public boolean checkPassword(String password){
        String regex="^.{6,}$";
        if(password.matches(regex)){
            System.out.println("Password is correct");
            return true;
        }
        System.out.println("Password is incorrect");
        System.out.println("You must enter minimum 6 symbols");
        return false;
    }

    public boolean checkPhone(String phone){
        String regex="[0-9]{10}";
        if(phone.matches(regex)){
            System.out.println("Phone is correct");
            return true;
        }
        System.out.println("Phone is incorrect");
        return false;
    }

}
