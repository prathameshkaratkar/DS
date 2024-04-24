import java.io.*;
import java.net.*;

public class BerkeleyServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(9876);
            System.out.println("Server is waiting for clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ServerThread(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServerThread extends Thread {
    private Socket clientSocket;

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Get client's time
            long clientTime = Long.parseLong(in.readLine());
            long serverTime = System.currentTimeMillis();

            // Send server time to the client
            out.println(serverTime);

            // Calculate the offset
            long offset = serverTime - clientTime;

            // Send offset to the client for clock adjustment
            out.println(offset);

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

