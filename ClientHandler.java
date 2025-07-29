package messenger2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class ClientHandler implements Runnable {
    Socket socket;
    String nickname;
    final DataInputStream dis;
    final DataOutputStream dos;
    boolean isLoggedIn;

    ClientHandler(Socket s, String nickname, DataInputStream dis, DataOutputStream dos) {
        this.socket = s;
        this.nickname = nickname;
        this.dis = dis;
        this.dos = dos;
        this.isLoggedIn = true;
    }

    @Override
    public void run() {
        String received;
        try {
            while (true) {
                received = dis.readUTF();
                received = decrypt(received);

                if (received.equalsIgnoreCase("exit")) {
                    break;
                }

                if (received.startsWith("@")) {
                    String[] split = received.split(" ", 2);
                    String recipient = split[0].substring(1);
                    String message = split[1];
                    ChatServer.privateMessage(message, recipient, this);
                } else {
                    ChatServer.broadcast(nickname + ": " + received, this);
                }
            }
        } catch (IOException e) {
            System.out.println("Client " + nickname + " disconnected unexpectedly.");
        } finally {
            isLoggedIn = false;
            ChatServer.removeClient(this);
            try {
                dis.close();
                dos.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void sendMessage(String msg) {
        try {
            msg = encrypt(msg);
            dos.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String encrypt(String message) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : message.toCharArray()) {
            encrypted.append((char) (c + 2));
        }
        return encrypted.toString();
    }

    private String decrypt(String message) {
        StringBuilder decrypted = new StringBuilder();
        for (char c : message.toCharArray()) {
            decrypted.append((char) (c - 2));
        }
        return decrypted.toString();
    }
}
