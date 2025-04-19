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
                String firstEmptyRowQuery = "SELECT COALESCE((SELECT MIN(t1.services_id + 1) FROM services t1 LEFT JOIN services t2 ON t1.services_id + 1 = t2.services_id WHERE t2.services_id IS NULL), (SELECT MAX(services_id) + 1 FROM services)) AS missingId";
                ps = connection.prepareStatement(firstEmptyRowQuery);
                rs = ps.executeQuery();
                int missingRowId = 0;

                if (rs.next()) {
                    missingRowId = rs.getInt("missingId");
                    System.out.println("Received the row id");
                }

                String sql="INSERT INTO services (services_id, name, description, price) VALUES (?, ?,?,?)";
                ps=connection.prepareStatement(sql);

                System.out.println("Enter name of the service: ");
                String name = input.nextLine();
                System.out.println("Enter description of the service: ");
                String description = input.nextLine();
                System.out.println("Enter price of the service");
                double price = input.nextDouble();

                ps.setInt(1, missingRowId);
                ps.setString(2, name);
                ps.setString(3, description);
                ps.setDouble(4, price);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0){
                    System.out.println("New service created successfully!");
                } else {
                    System.out.println("Couldn't add the new service");
                }

            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }

        @Override
        public void deleteServices(Admin admin) {
            try {
                connection=ConnectDatabase.connection();
                String sql="SELECT * FROM services";
                ps=connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs=ps.executeQuery();

                if(!rs.next()){
                    System.out.println("There are no registered services");
                    return;
                } else {
                    rs.beforeFirst();
                    while(rs.next()){
                        int id = rs.getInt("services_id");
                        String name = rs.getString("name");
                        String description = rs.getString("description");
                        double price = rs.getDouble("price");

                        System.out.println("Service id: "+id+" name: "+name+" description: "+description+" price: "+price);
                    }
                }

                System.out.println("Choose the id of the service you want to delete");
                int serviceId = input.nextInt();


                rs.beforeFirst();
                if(!serviceIDValidation(serviceId, rs)){
                    System.out.println("A service with such ID does not exist");
                    return;
                }

                String deleteQuery = "DELETE FROM services WHERE services_id = ?";
                ps=connection.prepareStatement(deleteQuery);
                ps.setInt(1,serviceId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0){
                    System.out.println("Deleted service with id " + serviceId);
                } else {
                    System.out.println("No service with id " + serviceId);
                }

            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void editServices(Admin admin) {
            try {
                connection=ConnectDatabase.connection();
                String sql="SELECT * FROM services";
                ps=connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs=ps.executeQuery();

                if(!rs.next()){
                    System.out.println("There are no registered services");
                    return;
                } else {
                    rs.beforeFirst();
                    while(rs.next()){
                        int id = rs.getInt("services_id");
                        String name = rs.getString("name");
                        String description = rs.getString("description");
                        double price = rs.getDouble("price");

                        System.out.println("Service id: "+id+" name: "+name+" description: "+description+" price: "+price);
                    }
                }

                System.out.println("Choose the id of the service you want to delete");
                int serviceIdForUpdate = input.nextInt();

                rs.beforeFirst();
                if(!serviceIDValidation(serviceIdForUpdate, rs)){
                    System.out.println("A service with such ID does not exist");
                    return;
                }

                int choice = 0;
                while (choice != 4){
                    System.out.println("Choose what to change: \n" +
                            "1-name\n" +
                            "2-description\n" +
                            "3-price\n" +
                            "4-End");

                    choice = input.nextInt();
                    int rowsAffected;

                    switch (choice) {
                        case 1:
                            System.out.println("Enter the new name");
                            input.nextLine();
                            String newName = input.nextLine();
                            String updateNameQuery="UPDATE services SET name = ? WHERE services_id = ?";
                            ps=connection.prepareStatement(updateNameQuery);

                            ps.setString(1, newName);
                            ps.setInt(2, serviceIdForUpdate);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0){
                                System.out.println("Updated name for service with id " + serviceIdForUpdate);
                            } else {
                                System.out.println("No service name was updated with id " + serviceIdForUpdate);
                            }

                            break;
                        case 2:
                            System.out.println("Enter the new description");
                            input.nextLine();
                            String newDescription = input.nextLine();
                            String updateDescriptionQuery="UPDATE services SET description = ? WHERE services_id = ?";
                            ps=connection.prepareStatement(updateDescriptionQuery);

                            ps.setString(1, newDescription);
                            ps.setInt(2, serviceIdForUpdate);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0){
                                System.out.println("Updated description for service with id " + serviceIdForUpdate);
                            } else {
                                System.out.println("No service description was updated with id " + serviceIdForUpdate);
                            }

                            break;
                        case 3:
                            System.out.println("Enter the new price");
                            double newPrice = input.nextDouble();
                            String updatePriceQuery="UPDATE services SET price = ? WHERE services_id = ?";
                            ps=connection.prepareStatement(updatePriceQuery);

                            ps.setDouble(1, newPrice);
                            ps.setInt(2, serviceIdForUpdate);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0){
                                System.out.println("Updated price for service with id " + serviceIdForUpdate);
                            } else {
                                System.out.println("No service price was updated with id " + serviceIdForUpdate);
                            }

                            break;
                    }
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void addUser(Admin admin) {
            try {
                connection = ConnectDatabase.connection();
                String firstEmptyRowQuery = "SELECT COALESCE((SELECT MIN(u1.user_id + 1) FROM user u1 LEFT JOIN user u2 ON u1.user_id + 1 = u2.user_id WHERE u2.user_id IS NULL), (SELECT MAX(user_id) + 1 FROM user)) AS missingId";
                ps = connection.prepareStatement(firstEmptyRowQuery);
                rs = ps.executeQuery();
                int missingRowId = 0;

                if (rs.next()) {
                    missingRowId = rs.getInt("missingId");
                    System.out.println("Received the row id");
                }

                String sql = "INSERT INTO user (user_id, name, email, password, phone, address, role) VALUES (?,?,?,?,?,?,?)";
                ps = connection.prepareStatement(sql);

                System.out.println("Enter name of the user: ");
                String userName = input.nextLine();

                System.out.println("Enter email of the user: ");
                String email;
                do {
                    email = input.nextLine();
                }while (!checkEmail(email));

                System.out.println("Enter password of the user");
                String password;
                do {
                    password = input.nextLine();
                }while (!checkPassword(password));

                System.out.println("Enter phone number of the user");
                String phoneNumber;
                do {
                    phoneNumber = input.nextLine();
                }while (!checkPhone(phoneNumber));

                System.out.println("Enter address of the user");
                String address = input.nextLine();

                System.out.println("Enter 'admin' or 'client' role for the new user");
                String role;
                do {
                    role = input.nextLine();
                } while (!role.equals("admin") && !role.equals("client"));

                ps.setInt(1, missingRowId);
                ps.setString(2, userName);
                ps.setString(3, email);
                ps.setString(4, password);
                ps.setString(5, phoneNumber);
                ps.setString(6, address);
                ps.setString(7, role);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("New user created successfully!");
                } else {
                    System.out.println("Couldn't add the new user");
                }

            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void deleteUser(Admin admin) {
            try {
                connection=ConnectDatabase.connection();
                String sql="SELECT * FROM user";
                ps=connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs=ps.executeQuery();

                if(!rs.next()){
                    System.out.println("There are no registered users");
                    return;
                } else {
                    rs.beforeFirst();
                    while(rs.next()){
                        System.out.println("User ID: " + rs.getInt("user_id") + ", " +
                                "Name: " + rs.getString("name") + ", " +
                                "Email: " + rs.getString("email") + ", " +
                                "Password: " + rs.getString("password") + ", " +
                                "Phone: " + rs.getString("phone") + ", " +
                                "Address: " + rs.getString("address") + ", " +
                                "Role: " + rs.getString("role"));
                    }
                }

                System.out.println("Choose the id of the user you want to delete");
                int userId = input.nextInt();

                rs.beforeFirst();
                if(!userIDValidation(userId, rs)){
                    System.out.println("A user with such ID does not exist");
                    return;
                }

                String deleteQuery = "DELETE FROM user WHERE user_id = ?";
                ps=connection.prepareStatement(deleteQuery);
                ps.setInt(1,userId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0){
                    System.out.println("Deleted user with id " + userId);
                } else {
                    System.out.println("No user with id " + userId);
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void editUser(Admin admin) {
            try{
                connection=ConnectDatabase.connection();
                String sql="SELECT * FROM user";
                ps=connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs=ps.executeQuery();

                if(!rs.next()){
                    System.out.println("There are no registered users");
                    return;
                } else {
                    rs.beforeFirst();
                    while(rs.next()){
                        System.out.println("User ID: " + rs.getInt("user_id") + ", " +
                                "Name: " + rs.getString("name") + ", " +
                                "Email: " + rs.getString("email") + ", " +
                                "Password: " + rs.getString("password") + ", " +
                                "Phone: " + rs.getString("phone") + ", " +
                                "Address: " + rs.getString("address") + ", " +
                                "Role: " + rs.getString("role"));
                    }
                }

                System.out.println("Choose the id of the user you want to delete");
                int userId = input.nextInt();

                rs.beforeFirst();
                if(!userIDValidation(userId, rs)){
                    System.out.println("A user with such ID does not exist");
                    return;
                }


                int choice = 0;
                while (choice != 7){
                    System.out.println("Choose what to change: \n" +
                            "1-name\n" +
                            "2-email\n" +
                            "3-password\n" +
                            "4-phone\n" +
                            "5-address\n" +
                            "6-role\n" +
                            "7-End");

                    choice = input.nextInt();

                    int rowsAffected;
                    String updateQuery;
                    ps = null;

                    switch (choice) {
                        case 1:
                            System.out.println("Enter the new name");
                            input.nextLine();
                            String newName = input.nextLine();
                            updateQuery = "UPDATE user SET name = ? WHERE user_id = ?";
                            ps = connection.prepareStatement(updateQuery);

                            ps.setString(1, newName);
                            ps.setInt(2, userId);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0){
                                System.out.println("Updated name for customer with id " + userId);
                            } else {
                                System.out.println("No customer name was updated with id " + userId);
                            }

                            break;
                        case 2:
                            System.out.println("Enter the new email");
                            input.nextLine();

                            String newEmail;
                            do {
                                newEmail = input.nextLine();
                            }while (!checkEmail(newEmail));

                            updateQuery = "UPDATE user SET email = ? WHERE user_id = ?";
                            ps = connection.prepareStatement(updateQuery);

                            ps.setString(1, newEmail);
                            ps.setInt(2, userId);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0){
                                System.out.println("Updated email for service with id " + userId);
                            } else {
                                System.out.println("No customer email was updated with id " + userId);
                            }

                            break;
                        case 3:
                            System.out.println("Enter the new password:");
                            input.nextLine();

                            String newPassword;
                            do {
                                newPassword = input.nextLine();
                            }while (!checkPassword(newPassword));

                            updateQuery = "UPDATE user SET password = ? WHERE user_id = ?";
                            ps = connection.prepareStatement(updateQuery);

                            ps.setString(1, newPassword);
                            ps.setInt(2, userId);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0){
                                System.out.println("Updated password for service with id " + userId);
                            } else {
                                System.out.println("No user password was updated with id " + userId);
                            }

                            break;
                        case 4:
                            System.out.println("Enter the new phone:");
                            input.nextLine();

                            String newPhone;
                            do {
                                newPhone = input.nextLine();
                            }while (!checkPhone(newPhone));

                            updateQuery = "UPDATE user SET phone = ? WHERE user_id = ?";

                            ps = connection.prepareStatement(updateQuery);
                            ps.setString(1, newPhone);
                            ps.setInt(2, userId);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0){
                                System.out.println("Updated phone for service with id " + userId);
                            } else {
                                System.out.println("No user phone was updated with id " + userId);
                            }

                            break;
                        case 5:
                            System.out.println("Enter the new address:");
                            input.nextLine();
                            String newAddress = input.nextLine();
                            updateQuery = "UPDATE user SET address = ? WHERE user_id = ?";
                            ps = connection.prepareStatement(updateQuery);

                            ps.setString(1, newAddress);
                            ps.setInt(2, userId);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0){
                                System.out.println("Updated address for service with id " + userId);
                            } else {
                                System.out.println("No user address was updated with id " + userId);
                            }

                            break;
                        case 6:
                            System.out.println("Enter the new role:");
                            input.nextLine();
                            String newRole = input.nextLine();
                            if (!newRole.equals("admin") && !newRole.equals("client")) {
                                System.out.println("Invalid role. Please enter 'admin' or 'client'.");
                                break;
                            }
                            updateQuery = "UPDATE user SET role = ? WHERE user_id = ?";
                            ps = connection.prepareStatement(updateQuery);

                            ps.setString(1, newRole);
                            ps.setInt(2, userId);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0){
                                System.out.println("Updated role for service with id " + userId);
                            } else {
                                System.out.println("No user role was updated with id " + userId);
                            }

                            break;
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void addCarService(Admin admin) {
            try {
                connection = ConnectDatabase.connection();
                String firstEmptyRowQuery = "SELECT COALESCE((SELECT MIN(cs1.carService_id + 1) FROM carservice cs1 LEFT JOIN carservice cs2 ON cs1.carService_id + 1 = cs2.carService_id WHERE cs2.carService_id IS NULL), (SELECT MAX(carService_id) + 1 FROM carservice)) AS missingId";
                ps = connection.prepareStatement(firstEmptyRowQuery);
                rs = ps.executeQuery();
                int missingRowId = 0;

                if (rs.next()) {
                    missingRowId = rs.getInt("missingId");
                    System.out.println("Received the row id");
                }

                String sql = "INSERT INTO carservice (carService_id, name, location, email, phone) VALUES (?,?,?,?,?)";
                ps = connection.prepareStatement(sql);

                System.out.println("Enter name of the car service: ");
                String serviceName = input.nextLine();

                System.out.println("Enter location of the car service: ");
                String location = input.nextLine();

                System.out.println("Enter email of the service: ");
                String email;
                do {
                    email = input.nextLine();
                }while (!checkEmail(email));

                System.out.println("Enter phone number of the service");
                String phoneNumber;
                do {
                    phoneNumber = input.nextLine();
                }while (!checkPhone(phoneNumber));

                ps.setInt(1, missingRowId);
                ps.setString(2, serviceName);
                ps.setString(3, location);
                ps.setString(4, email);
                ps.setString(5, phoneNumber);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("New car service added successfully!");
                } else {
                    System.out.println("Couldn't add the new car service");
                }

            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void deleteCarService(Admin admin) {
            try {
                connection = ConnectDatabase.connection();
                String sql = "SELECT * FROM carService";
                ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs = ps.executeQuery();

                if (!rs.next()) {
                    System.out.println("There are no registered car services.");
                    return;
                } else {
                    rs.beforeFirst();
                    while (rs.next()) {
                        System.out.println("Car Service ID: " + rs.getInt("carService_id") + ", " +
                                "Name: " + rs.getString("name") + ", " +
                                "Location: " + rs.getString("location") + ", " +
                                "Email: " + rs.getString("email") + ", " +
                                "Phone: " + rs.getString("phone"));
                    }
                }

                System.out.println("Choose the ID of the car service you want to delete:");
                int carServiceId = input.nextInt();

                rs.beforeFirst();
                if(!carServiceIDValidation(carServiceId, rs)){
                    System.out.println("A car service with such ID does not exist");
                    return;
                }

                String deleteQuery = "DELETE FROM carService WHERE carService_id = ?";
                ps = connection.prepareStatement(deleteQuery);
                ps.setInt(1, carServiceId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Deleted car service with ID " + carServiceId);
                } else {
                    System.out.println("No car service found with ID " + carServiceId);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void editCarService(Admin admin) {
            try {
                connection = ConnectDatabase.connection();
                String sql = "SELECT * FROM carService";
                ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs = ps.executeQuery();

                if (!rs.next()) {
                    System.out.println("There are no registered car services.");
                    return;
                } else {
                    rs.beforeFirst();
                    while (rs.next()) {
                        int id = rs.getInt("carService_id");
                        String name = rs.getString("name");
                        String location = rs.getString("location");
                        String email = rs.getString("email");
                        String phone = rs.getString("phone");

                        System.out.println("Car Service ID: " + id + ", " +
                                "Name: " + name + ", " +
                                "Location: " + location + ", " +
                                "Email: " + email + ", " +
                                "Phone: " + phone);
                    }
                }

                System.out.println("Choose the ID of the car service you want to update:");
                int carServiceIdForUpdate = input.nextInt();

                rs.beforeFirst();
                if(!carServiceIDValidation(carServiceIdForUpdate, rs)){
                    System.out.println("A car service with such ID does not exist");
                    return;
                }

                int choice = 0;
                while (choice != 5) {
                    System.out.println("Choose what to change: \n" +
                            "1 - Name\n" +
                            "2 - Location\n" +
                            "3 - Email\n" +
                            "4 - Phone\n" +
                            "5 - End");

                    choice = input.nextInt();
                    int rowsAffected;

                    switch (choice) {
                        case 1:
                            System.out.println("Enter the new name:");
                            input.nextLine();
                            String newName = input.nextLine();
                            String updateNameQuery = "UPDATE carService SET name = ? WHERE carService_id = ?";
                            ps = connection.prepareStatement(updateNameQuery);
                            ps.setString(1, newName);
                            ps.setInt(2, carServiceIdForUpdate);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Updated name for car service with ID " + carServiceIdForUpdate);
                            } else {
                                System.out.println("No car service name was updated with ID " + carServiceIdForUpdate);
                            }
                            break;

                        case 2:
                            System.out.println("Enter the new location:");
                            input.nextLine();
                            String newLocation = input.nextLine();
                            String updateLocationQuery = "UPDATE carService SET location = ? WHERE carService_id = ?";
                            ps = connection.prepareStatement(updateLocationQuery);
                            ps.setString(1, newLocation);
                            ps.setInt(2, carServiceIdForUpdate);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Updated location for car service with ID " + carServiceIdForUpdate);
                            } else {
                                System.out.println("No car service location was updated with ID " + carServiceIdForUpdate);
                            }
                            break;

                        case 3:
                            System.out.println("Enter the new email:");
                            input.nextLine();

                            String newEmail;
                            do {
                                newEmail = input.nextLine();
                            }while (!checkEmail(newEmail));
                            String updateEmailQuery = "UPDATE carService SET email = ? WHERE carService_id = ?";
                            ps = connection.prepareStatement(updateEmailQuery);
                            ps.setString(1, newEmail);
                            ps.setInt(2, carServiceIdForUpdate);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Updated email for car service with ID " + carServiceIdForUpdate);
                            } else {
                                System.out.println("No car service email was updated with ID " + carServiceIdForUpdate);
                            }
                            break;

                        case 4:
                            System.out.println("Enter the new phone number:");
                            input.nextLine();

                            String newPhone;
                            do {
                                newPhone = input.nextLine();
                            }while (!checkPhone(newPhone));

                            String updatePhoneQuery = "UPDATE carService SET phone = ? WHERE carService_id = ?";
                            ps = connection.prepareStatement(updatePhoneQuery);
                            ps.setString(1, newPhone);
                            ps.setInt(2, carServiceIdForUpdate);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Updated phone number for car service with ID " + carServiceIdForUpdate);
                            } else {
                                System.out.println("No car service phone number was updated with ID " + carServiceIdForUpdate);
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void deleteRequest(Admin admin) {
            try {
                connection = ConnectDatabase.connection();
                String sql = "SELECT * FROM request";
                ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs = ps.executeQuery();

                if (!rs.next()) {
                    System.out.println("There are no registered requests.");
                    return;
                } else {
                    rs.beforeFirst();
                    while (rs.next()) {
                        System.out.println("Request ID: " + rs.getInt("request_id") + ", " +
                                "Car ID: " + rs.getInt("car_id") + ", " +
                                "User ID: " + rs.getInt("user_id") + ", " +
                                "Status: " + rs.getString("status") + ", " +
                                "Created On: " + rs.getTimestamp("createdOn") + ", " +
                                "Completed On: " + rs.getTimestamp("completedOn") + ", " +
                                "Modified Request: " + rs.getBoolean("modified_request"));
                    }
                }

                System.out.println("Choose the ID of the request you want to delete:");
                int requestId = input.nextInt();

                rs.beforeFirst();
                if(!requestIDValidation(requestId, rs)){
                    System.out.println("A request with such ID does not exist");
                    return;
                }

                String deleteQuery = "DELETE FROM request WHERE request_id = ?";
                ps = connection.prepareStatement(deleteQuery);
                ps.setInt(1, requestId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Deleted request with ID " + requestId);
                } else {
                    System.out.println("No request found with ID " + requestId);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void modifyRequest(Admin admin) {
            try {
                connection = ConnectDatabase.connection();
                String sql = "SELECT * FROM request";
                ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs = ps.executeQuery();

                if (!rs.next()) {
                    System.out.println("There are no registered requests.");
                    return;
                } else {
                    rs.beforeFirst();
                    while (rs.next()) {
                        System.out.println("Request ID: " + rs.getInt("request_id") + ", " +
                                "Car ID: " + rs.getInt("car_id") + ", " +
                                "User ID: " + rs.getInt("user_id") + ", " +
                                "Status: " + rs.getString("status") + ", " +
                                "Created On: " + rs.getTimestamp("createdOn") + ", " +
                                "Completed On: " + rs.getTimestamp("completedOn") + ", " +
                                "Modified Request: " + rs.getBoolean("modified_request"));
                    }
                }

                System.out.println("Choose the ID of the request you want to update:");
                int requestIdForUpdate = input.nextInt();

                rs.beforeFirst();
                if(!requestIDValidation(requestIdForUpdate, rs)){
                    System.out.println("A request with such ID does not exist");
                    return;
                }

                System.out.println("Enter the new status 'approved', 'completed', 'rejected'");
                input.nextLine();
                String newStatus = input.nextLine();
                int rowsAffected;

                while(!newStatus.equals("approved") && !newStatus.equals("completed") && !newStatus.equals("rejected")){
                    System.out.println("The entered status is invalid!");
                    System.out.println("Enter the new status 'approved', 'completed', 'rejected'");
                    newStatus = input.nextLine();
                }

                String updateStatusQuery = "UPDATE request SET status = ?, modified_request = true WHERE request_id = ?";
                ps = connection.prepareStatement(updateStatusQuery);

                ps.setString(1, newStatus);
                ps.setInt(2, requestIdForUpdate);

                rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Updated status for request with ID " + requestIdForUpdate);
                } else {
                    System.out.println("No request status was updated for ID " + requestIdForUpdate);
                }

                if(newStatus.equals("completed")){
                    String updateCompletedOnQuery = "UPDATE request SET completedOn = CURRENT_TIMESTAMP, modified_request = true WHERE request_id = ?";
                    ps = connection.prepareStatement(updateCompletedOnQuery);

                    ps.setInt(1, requestIdForUpdate);

                    rowsAffected = ps.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Marked request with ID " + requestIdForUpdate + " as completed.");
                    } else {
                        System.out.println("No request was marked as completed for ID " + requestIdForUpdate);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void addCar(Admin admin) {
            try {
                connection = ConnectDatabase.connection();
                String firstEmptyRowQuery = "SELECT COALESCE((SELECT MIN(c1.car_id + 1) FROM car c1 LEFT JOIN car c2 ON c1.car_id + 1 = c2.car_id WHERE c2.car_id IS NULL), (SELECT MAX(car_id) + 1 FROM car)) AS missingId";
                ps = connection.prepareStatement(firstEmptyRowQuery);
                rs = ps.executeQuery();
                int missingRowId = 0;

                if (rs.next()) {
                    missingRowId = rs.getInt("missingId");
                    System.out.println("Received the row ID");
                }

                String sql = "INSERT INTO car (car_id, user_id, brand, model, year, rg_number) VALUES (?,?,?,?,?,?)";
                ps = connection.prepareStatement(sql);

                System.out.println("Enter user ID: ");
                int userId = input.nextInt();
                input.nextLine();

                System.out.println("Enter car brand: ");
                String brand = input.nextLine();

                System.out.println("Enter car model: ");
                String model = input.nextLine();

                System.out.println("Enter year of manufacturing: ");
                int year = input.nextInt();
                input.nextLine();

                System.out.println("Enter registration number: ");
                String rgNumber = input.nextLine();

                ps.setInt(1, missingRowId);
                ps.setInt(2, userId);
                ps.setString(3, brand);
                ps.setString(4, model);
                ps.setInt(5, year);
                ps.setString(6, rgNumber);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("New car added successfully!");
                } else {
                    System.out.println("Couldn't add the new car");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void deleteCar(Admin admin) {
            try {
                connection = ConnectDatabase.connection();
                String sql = "SELECT * FROM car";
                ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs = ps.executeQuery();

                if (!rs.next()) {
                    System.out.println("There are no registered cars.");
                    return;
                } else {
                    rs.beforeFirst();
                    while (rs.next()) {
                        System.out.println("Car ID: " + rs.getInt("car_id") + ", " +
                                "User ID: " + rs.getInt("user_id") + ", " +
                                "Brand: " + rs.getString("brand") + ", " +
                                "Model: " + rs.getString("model") + ", " +
                                "Year: " + rs.getInt("year") + ", " +
                                "Registration Number: " + rs.getString("rg_number"));
                    }
                }

                System.out.println("Choose the ID of the car you want to delete:");
                int carId = input.nextInt();

                rs.beforeFirst();
                if(!carIDValidation(carId, rs)){
                    System.out.println("A car with such ID does not exist");
                    return;
                }

                String deleteQuery = "DELETE FROM car WHERE car_id = ?";
                ps = connection.prepareStatement(deleteQuery);
                ps.setInt(1, carId);

                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Deleted car with ID " + carId);
                } else {
                    System.out.println("No car found with ID " + carId);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        @Override
        public void editCar(Admin admin) {
            try {
                connection = ConnectDatabase.connection();
                String sql = "SELECT * FROM car";
                ps = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                rs = ps.executeQuery();

                if (!rs.next()) {
                    System.out.println("There are no registered cars.");
                    return;
                } else {
                    rs.beforeFirst();
                    while (rs.next()) {
                        int id = rs.getInt("car_id");
                        int userId = rs.getInt("user_id");
                        String brand = rs.getString("brand");
                        String model = rs.getString("model");
                        int year = rs.getInt("year");
                        String rgNumber = rs.getString("rg_number");

                        System.out.println("Car ID: " + id + ", " +
                                "User ID: " + userId + ", " +
                                "Brand: " + brand + ", " +
                                "Model: " + model + ", " +
                                "Year: " + year + ", " +
                                "Registration Number: " + rgNumber);
                    }
                }

                System.out.println("Choose the ID of the car you want to update:");
                int carIdForUpdate = input.nextInt();

                rs.beforeFirst();
                if(!carIDValidation(carIdForUpdate, rs)){
                    System.out.println("A car with such ID does not exist");
                    return;
                }

                int choice = 0;
                while (choice != 5) {
                    System.out.println("Choose what to change: \n" +
                            "1 - Brand\n" +
                            "2 - Model\n" +
                            "3 - Year\n" +
                            "4 - Registration Number\n" +
                            "5 - End");

                    choice = input.nextInt();
                    int rowsAffected;

                    switch (choice) {
                        case 1:
                            System.out.println("Enter the new brand:");
                            input.nextLine();
                            String newBrand = input.nextLine();
                            String updateBrandQuery = "UPDATE car SET brand = ? WHERE car_id = ?";
                            ps = connection.prepareStatement(updateBrandQuery);
                            ps.setString(1, newBrand);
                            ps.setInt(2, carIdForUpdate);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Updated brand for car with ID " + carIdForUpdate);
                            } else {
                                System.out.println("No brand was updated for car ID " + carIdForUpdate);
                            }
                            break;

                        case 2:
                            System.out.println("Enter the new model:");
                            input.nextLine();
                            String newModel = input.nextLine();
                            String updateModelQuery = "UPDATE car SET model = ? WHERE car_id = ?";
                            ps = connection.prepareStatement(updateModelQuery);
                            ps.setString(1, newModel);
                            ps.setInt(2, carIdForUpdate);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Updated model for car with ID " + carIdForUpdate);
                            } else {
                                System.out.println("No model was updated for car ID " + carIdForUpdate);
                            }
                            break;

                        case 3:
                            System.out.println("Enter the new year:");
                            int newYear = input.nextInt();
                            String updateYearQuery = "UPDATE car SET year = ? WHERE car_id = ?";
                            ps = connection.prepareStatement(updateYearQuery);
                            ps.setInt(1, newYear);
                            ps.setInt(2, carIdForUpdate);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Updated year for car with ID " + carIdForUpdate);
                            } else {
                                System.out.println("No year was updated for car ID " + carIdForUpdate);
                            }
                            break;

                        case 4:
                            System.out.println("Enter the new registration number:");
                            input.nextLine();
                            String newRgNumber = input.nextLine();
                            String updateRgNumberQuery = "UPDATE car SET rg_number = ? WHERE car_id = ?";
                            ps = connection.prepareStatement(updateRgNumberQuery);
                            ps.setString(1, newRgNumber);
                            ps.setInt(2, carIdForUpdate);

                            rowsAffected = ps.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Updated registration number for car with ID " + carIdForUpdate);
                            } else {
                                System.out.println("No registration number was updated for car ID " + carIdForUpdate);
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
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

        private boolean requestIDValidation(int requestIdForUpdate, ResultSet rs) {
            try{
                while (rs.next()){
                    if(rs.getInt("request_id") == requestIdForUpdate){
                        return true;
                    }
                }
            }catch (Exception e){
                System.out.println("Error while validating request ID: " + e.getMessage());
            }
            return false;
        }

        private boolean carIDValidation(int carId, ResultSet rs) {
            try{
                while (rs.next()){
                    if(rs.getInt("car_id") == carId){
                        return true;
                    }
                }
            }catch (Exception e){
                System.out.println("Error while validating car ID: " + e.getMessage());
            }
            return false;
        }

        private boolean carServiceIDValidation(int carServiceIdForUpdate, ResultSet rs) {
            try{
                while (rs.next()){
                    if(rs.getInt("carService_id") == carServiceIdForUpdate){
                        return true;
                    }
                }
            }catch (Exception e){
                System.out.println("Error while validating car service ID: " + e.getMessage());
            }
            return false;
        }

        private boolean userIDValidation(int userId, ResultSet rs) {
            try{
                while (rs.next()){
                    if(rs.getInt("user_id") == userId){
                        return true;
                    }
                }
            }catch (Exception e){
                System.out.println("Error while validating user ID: " + e.getMessage());
            }
            return false;
        }

        private boolean serviceIDValidation(int serviceId, ResultSet rs) {
            try{
                while (rs.next()){
                    if(rs.getInt("services_id") == serviceId){
                        return true;
                    }
                }
            }catch (Exception e){
                System.out.println("Error while validating service ID: " + e.getMessage());
            }
            return false;
        }







    //////////////////////////////////////////////////////My requests//////////////////////////////////////////////////
/*
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
    public void editServices(Admin admin) throws SQLException {
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
        }finally {
            connection.close();
            ps.close();
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
    /
*/

}
