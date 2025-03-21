package InterfacePackage;

import org.example.Admin;

public interface AdminActions {


    void addServices(Admin admin);
    void deleteServices(Admin admin);
    void editServices(Admin admin);

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



}
