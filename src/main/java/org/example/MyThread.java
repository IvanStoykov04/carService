package org.example;

import ConnetctDatabase.AuthenticationManager;

import java.net.Socket;

public class MyThread implements Runnable{

    private Socket socketClient;
    public MyThread(Socket socket){
        this.socketClient=socketClient;
    }

    @Override
    public void run() {
        AuthenticationManager authenticationManager=new AuthenticationManager();
        authenticationManager.mainMenu();
    }
}
