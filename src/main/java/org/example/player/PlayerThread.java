package org.example.player;

public class PlayerThread implements Runnable {
    private final Player player;

    public PlayerThread(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        try {
            if (player.isInitiator()) {
                for (int i = 0; i < 10; i++) {
                    player.sendMessage("Message");
                    player.receiveMessage();
                }
            } else {
                while (player.getMessageCount() < 10) {
                    String receivedMessage = player.receiveMessage();
                    player.sendMessage(receivedMessage);
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
