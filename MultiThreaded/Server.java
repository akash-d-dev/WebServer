
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

    public Consumer<Socket> getConsumer() {
        return clientSocket -> {
            try {
                System.out.println("Accepted connection from " + clientSocket.getRemoteSocketAddress());
                PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
                toClient.println("Hello from the server!");
                toClient.close();
                clientSocket.close();

            } catch (Exception e) {
                System.err.println("Error accepting connection: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }

    public static void main(String[] args) {
        int port = 8010;
        Server server = new Server();
        try {
            ServerSocket socket = new ServerSocket(port);
            socket.setSoTimeout(10000);
            System.out.println("Server is listening on " + port);

            while (true) {
                Socket acceptedConnection = socket.accept();
                System.out.println("Accepted connection from " + acceptedConnection.getRemoteSocketAddress());
                Thread thread = new Thread(() -> server.getConsumer().accept(acceptedConnection));
                // server.getConsumer().accept(acceptedConnection);
                thread.start();
            }

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
