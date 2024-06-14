package org.example.player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class PlayerServer {
    private static ServerSocket serverSocket;
    private static Socket clientSocket;

    public static void main(String[] args) {
        int port = 5001;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutting down server...");
            closeConnections();
        }));

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started on port " + port);
            clientSocket = serverSocket.accept();

            try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("PlayerServer Received: " + inputLine);
                    out.println(inputLine + " 1");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeConnections();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void closeConnections() {
        try {
            if (clientSocket != null && !clientSocket.isClosed()) {
                clientSocket.close();
            }
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
