package messenger2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ChatServer {
    private static Vector<ClientHandler> clients = new Vector<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Server started on port 1234...");

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("New client connected: " + socket);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF("Enter your nickname: ");
            String nickname = dis.readUTF();

            ClientHandler newClient = new ClientHandler(socket, nickname, dis, dos);
            clients.add(newClient);
            Thread t = new Thread(newClient);
            t.start();

            logConnection(nickname);
        }
    }

    private static void logConnection(String nickname) {
        System.out.println("User '" + nickname + "' has joined.");
        for (ClientHandler client : clients) {
            client.sendMessage("User '" + nickname + "' has joined.");
        }
    }

    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public static void privateMessage(String message, String recipient, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client.nickname.equals(recipient)) {
                client.sendMessage("[Private] " + sender.nickname + ": " + message);
                return;
            }
        }
        sender.sendMessage("User not found.");
    }

    public static void removeClient(ClientHandler client) {
        clients.remove(client);
        broadcast("User '" + client.nickname + "' has left the chat.", client);
        System.out.println(client.nickname + " disconnected.");
    }
}
