package org.example;
import java.sql.*;
import java.util.Scanner;

import ConnetctDatabase.ClientRequests;
import ConnetctDatabase.ConnectDatabase;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner input=new Scanner(System.in);

        Client client=new Client("Ivan","ewcwec","123","098","ad");
        client.setId(2);
        ClientRequests clientRequests=new ClientRequests();
        clientRequests.addCar(client,input);
    }
}