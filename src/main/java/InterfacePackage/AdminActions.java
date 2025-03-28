package InterfacePackage;

import org.example.Admin;

import java.sql.SQLException;

public interface AdminActions {


    void addServices(Admin admin);
    void deleteServices(Admin admin);
    void editServices(Admin admin) throws SQLException;

    void addUser(Admin admin);
    void deleteUser(Admin admin);
    void editUser(Admin admin);

    void addCarService(Admin admin);
    void deleteCarService(Admin admin);
    void editCarService(Admin admin);

    void deleteRequest(Admin admin);
    void modifyRequest(Admin admin);

    void addCar(Admin admin);
    void deleteCar(Admin admin);
    void editCar(Admin admin);

    boolean checkPhone(String phone);
    boolean checkEmail(String email);
    boolean checkPassword(String password);

    /*
    void viewAllServices(Admin admin);

    void viewAllCar(Admin admin);

    void deleteRequestService(Admin admin,int requestId);
    void viewAllCarServices(Admin admin);
    void viewAllRequest(Admin admin);
*/



}
