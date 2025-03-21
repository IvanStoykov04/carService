package InterfacePackage;

import org.example.Client;
import org.example.Service;

public interface ClientActions {

    Service getService(Client client,int serviceId);
    void viewServices(Client client);
    void addCar(Client client);
    void viewCar(Client client);
    void addRequest(Client client);
    void viewRequestHistory(Client client);
    void requestModification(Client client);
    void updateUserData(Client client);
    boolean checkPassword(String password);


}
