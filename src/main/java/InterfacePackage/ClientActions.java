package InterfacePackage;

import org.example.Client;

public interface ClientActions {

    void viewServices(Client client);
    void addCar(Client client);
    void viewCar(Client client);
    void addRequest(Client client);
    void viewRequestHistory(Client client);
    void requestModification(Client client);
    void updateUserData(Client client);


}
