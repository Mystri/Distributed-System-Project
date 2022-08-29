import java.io.IOException;
import java.net.ServerSocket;

/**
 * Main class as a server. This class will be able to handle multiple client
 * connections.
 */
public class SocketMultipleServer {
    private ServerSocket serverSocket;

    public static void main(String[] args) {
        SocketMultipleServer server = new SocketMultipleServer();
        server.start();
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(123);
            System.out.println("Listening for a connection");
            while (true) {
                System.out.println("Received a connection");
                new SocketServer(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}