package org.example.player;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || "same-process".equals(args[0])) {
            runInSameProcess();
        } else if ("separate-processes".equals(args[0])) {
            runInSeparateProcesses();
        } else {
            System.out.println("Invalid argument. Use 'same-process' or 'separate-processes'.");
        }
    }

    private static void runInSameProcess() {
        BlockingQueue<String> queue1 = new ArrayBlockingQueue<>(1);
        BlockingQueue<String> queue2 = new ArrayBlockingQueue<>(1);

        Player initiator = new Player("Player1", queue1, queue2, true);
        Player responder = new Player("Player2", queue2, queue1, false);

        Thread initiatorThread = new Thread(new PlayerThread(initiator));
        Thread responderThread = new Thread(new PlayerThread(responder));

        initiatorThread.start();
        responderThread.start();

        try {
            initiatorThread.join();
            responderThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void runInSeparateProcesses() {
        try {
            ProcessBuilder pb1 = new ProcessBuilder("java", "-cp", "target/classes", "org.example.player.PlayerServer", "5001");
            ProcessBuilder pb2 = new ProcessBuilder("java", "-cp", "target/classes", "org.example.player.PlayerClient", "5001");
            pb1.inheritIO().start();
            pb2.inheritIO().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
