package InterfacePackage;

import org.example.Client;
import org.example.Request;
import org.example.Service;

public interface ClientActions {

    Service getService(Client client,int serviceId);
    void viewServices(Client client);
    boolean addCar(Client client,String brand,String model,int year,String rgNumber);
    void viewCar(Client client);
    boolean addRequest(Client client);
    boolean viewRequestHistory(Client client);
    boolean requestModification(Client client,int requestId);
    void updateUserData(Client client);
    boolean checkPassword(String password);
    void addRequestServices(Client client, Request request,Service service);



}
