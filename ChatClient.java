package messenger2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame implements ActionListener {
    private static JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private DataOutputStream dos;
    private DataInputStream dis;
    private Socket socket;

    ChatClient(String nickname) {
        setTitle("Chat - " + nickname);
        setSize(400, 500);
        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(sendButton, BorderLayout.EAST);

        add(new JScrollPane(chatArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(this);
        inputField.addActionListener(this);

        try {
            socket = new Socket("localhost", 1234);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            dis.readUTF(); // prompt from server
            dos.writeUTF(nickname);

            new Thread(() -> {
                while (true) {
                    try {
                        String msg = dis.readUTF();
                        chatArea.append(decrypt(msg) + "\n");
                    } catch (IOException e) {
                        chatArea.append("Disconnected from server.\n");
                        break;
                    }
                }
            }).start();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unable to connect to server.");
            e.printStackTrace();
            System.exit(0);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = inputField.getText().trim();
        if (!msg.isEmpty()) {
            try {
                chatArea.append("You: " + msg + "\n");
                dos.writeUTF(encrypt(msg));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            inputField.setText("");
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

    public static void main(String[] args) {
        String nickname = JOptionPane.showInputDialog("Enter nickname:");
        if (nickname != null && !nickname.trim().isEmpty()) {
            new ChatClient(nickname);
        }
    }
}
