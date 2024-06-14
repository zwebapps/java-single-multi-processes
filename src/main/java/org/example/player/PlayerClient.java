package org.example.player;

import java.io.IOException;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class PlayerClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            for (int i = 0; i < 10; i++) {
                String message = "Message " + (i + 1);
                out.println(message);
                System.out.println("PlayerClient Sent: " + message);
                String response = in.readLine();
                System.out.println("PlayerClient Received: " + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
