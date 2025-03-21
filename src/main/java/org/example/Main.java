package org.example;
import java.sql.*;
import java.util.Scanner;
import java.util.*;

import ConnetctDatabase.AuthenticationManager;
import ConnetctDatabase.ClientRequests;
import ConnetctDatabase.ConnectDatabase;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner input=new Scanner(System.in);

        AuthenticationManager authenticationManager=new AuthenticationManager();
        authenticationManager.mainMenu();
    }
}