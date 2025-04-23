package org.example;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientServer {

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("192.168.56.1", 1222)) {
            PrintStream printToServer = new PrintStream(socket.getOutputStream());
            Scanner inputFromServer=new Scanner(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);
            System.out.println("Client connected: " + socket.getInetAddress());
            System.out.println(inputFromServer.nextLine());
            // Create a separate thread to continuously receive server responses
            startResponseReaderThread(socket);

            while (true) {
                String command = scanner.nextLine();
                if (command.equals("exit")) {
                    break;
                }
                // Send command to server
                printToServer.println(command);
            }
        }
    }


    private static void startResponseReaderThread(Socket socket) {
        Thread responseThread = new Thread(() -> {
            try {
                Scanner serverResponse = new Scanner(socket.getInputStream());
                while (serverResponse.hasNextLine()) {
                    String response = serverResponse.nextLine();
                    System.out.println(response);
                }
            } catch (IOException e) {
                System.out.println("Server disconnected");
            }
        });
        responseThread.start();
    }
}
