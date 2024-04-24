import java.io.*;
import java.net.*;

public class BerkeleyClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9876);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Send client's time to the server
            out.println(System.currentTimeMillis());

            // Receive server time
            long serverTime = Long.parseLong(in.readLine());

            // Receive offset and adjust client's clock
            long offset = Long.parseLong(in.readLine());
            long adjustedTime = System.currentTimeMillis() + offset;

            System.out.println("Server time: " + serverTime);
            System.out.println("Adjusted client time: " + adjustedTime);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

