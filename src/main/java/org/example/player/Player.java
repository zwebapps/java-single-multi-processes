package org.example.player;

import java.util.concurrent.BlockingQueue;

public class Player {
    private final String name;
    private final BlockingQueue<String> incomingQueue;
    private final BlockingQueue<String> outgoingQueue;
    private final boolean isInitiator;
    private int messageCount;

    public Player(String name, BlockingQueue<String> incomingQueue, BlockingQueue<String> outgoingQueue, boolean isInitiator) {
        this.name = name;
        this.incomingQueue = incomingQueue;
        this.outgoingQueue = outgoingQueue;
        this.isInitiator = isInitiator;
        this.messageCount = 0;
    }

    public void sendMessage(String message) throws InterruptedException {
        outgoingQueue.put(message + " " + messageCount);
        messageCount++;
        System.out.println(name + " sent: " + message + " " + messageCount);
    }

    public String receiveMessage() throws InterruptedException {
        String message = incomingQueue.take();
        System.out.println(name + " received: " + message);
        return message;
    }

    public boolean isInitiator() {
        return isInitiator;
    }

    public int getMessageCount() {
        return messageCount;
    }
}
