import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public void run() throws IOException {
        int port = 8010;
        try (ServerSocket socket = new ServerSocket(port)) {
            socket.setSoTimeout(10000);
            while (true) {
                System.err.println("Server is listening on " + port);
                try {
                    Socket acceptedConnection = socket.accept();
                    System.out.println("Accepted connection from " + acceptedConnection.getRemoteSocketAddress());
                    PrintWriter toClient = new PrintWriter(acceptedConnection.getOutputStream());
                    BufferedReader fromClient = new BufferedReader(
                            new InputStreamReader(acceptedConnection.getInputStream()));
                    toClient.println("Hello, client!");
                    toClient.close();
                    fromClient.close();
                    acceptedConnection.close();
                } catch (IOException e) {
                    System.err.println("Error accepting connection: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        try {
            server.run();
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}