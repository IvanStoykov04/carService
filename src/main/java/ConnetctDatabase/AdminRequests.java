package ConnetctDatabase;

import InterfacePackage.AdminActions;
import org.example.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class AdminRequests implements AdminActions {

    Scanner input=new Scanner(System.in);
    PreparedStatement ps;
    ResultSet rs;
    Connection connection;

    @Override
    public void addServices(Admin admin) {

    }

    @Override
    public void deleteServices(Admin admin) {

    }

    @Override
    public void editServices(Admin admin) {

    }

    @Override
    public void addUser(Admin admin) {

    }

    @Override
    public void deleteUser(Admin admin) {

    }

    @Override
    public void editUser(Admin admin) {

    }

    @Override
    public void addCarService(Admin admin) {

    }

    @Override
    public void deleteCarService(Admin admin) {

    }

    @Override
    public void editCarService(Admin admin) {

    }

    @Override
    public void deleteRequest(Admin admin) {

    }

    @Override
    public void modifyRequest(Admin admin) {

    }

    @Override
    public void addCar(Admin admin) {

    }

    @Override
    public void deleteCar(Admin admin) {

    }

    @Override
    public void editCar(Admin admin) {

    }
}
