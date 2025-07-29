# 💬 Java Chat Application

A simple **real-time chat application** built using **Java Sockets**, **Threads**, and **Swing GUI**, allowing multiple users to chat with each other in a group or privately. This project demonstrates Java networking, basic encryption, and multithreaded programming concepts.

---

## 🚀 Features

- ✅ Group messaging support
- ✅ Private messaging using `@username`
- ✅ User nickname prompt
- ✅ Real-time messaging with multi-client support
- ✅ Simple message encryption (+2 ASCII shift)
- ✅ Join/leave notifications
- ✅ Java Swing GUI for clients

---

## 🛠️ Tools & Technologies

- Java (JDK 8 or above)
- Java Sockets (TCP/IP)
- Swing (GUI framework)
- Multithreading
- DataInputStream & DataOutputStream

---

## 📁 Project Structure
Chat-app/
├── messenger2/

│ ├── ChatServer.java # Main server that accepts and handles client sockets

│ ├── ClientHandler.java # Runnable class for individual client handling

│ └── ChatClient.java # GUI client application

---

## 📸 Screenshots

<img width="1144" height="562" alt="chatapp3" src="https://github.com/user-attachments/assets/2e7fa288-3303-436c-8deb-9c564d38c552" />
<img width="1221" height="724" alt="chatapp1" src="https://github.com/user-attachments/assets/56663fe8-5ec8-48e4-a728-9e96d3f7f173" />
<img width="891" height="702" alt="chatapp2" src="https://github.com/user-attachments/assets/f238e465-a0d9-4539-b955-e2ac43754e68" />


---

## 🧪 How to Run

### 1. Clone the repository
```bash
git clone https://github.com/Mugilan2003/Chat-app.git
cd Chat-app

### 2. Compile and Run the Server
javac messenger2/ChatServer.java messenger2/ClientHandler.java
java messenger2.ChatServer

### 3. Compile and Run the Client (in a new terminal)
javac messenger2/ChatClient.java
java messenger2.ChatClient

## 🔐 Message Encryption

A simple Caesar cipher is used:

* Encryption: Each character shifted by `+2` ASCII
* Decryption: Shifted back by `-2` ASCII


## 📦 Future Enhancements

* User authentication
* Emoji and file sharing
* Better encryption
* Online user list

---

## 👨‍💻 Author

**Mugilan A**
📧 [almugilan180@gmail.com](mailto:almugilan180@gmail.com)
🌐 [GitHub Profile](https://github.com/Mugilan2003)

---

## 📄 License

This project is open-source and free to use for educational purposes.
