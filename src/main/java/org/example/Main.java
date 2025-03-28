package org.example;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner input=new Scanner(System.in);


        ServerSocket serverSocket=null;
        try{
            serverSocket=new ServerSocket(1222);
            System.out.println("Server listening......");
            while(true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client with IP: "+socket.getInetAddress()+" is connected");
                MyThread myThread=new MyThread(socket);
                Thread thread=new Thread(myThread);
                thread.start();
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}