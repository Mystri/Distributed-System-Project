import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

// TODO: Handle one client for now. Maybe need to handle multiple
public class SocketServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public static void main(String[] args) {
        SocketServer server = new SocketServer();
        server.startServer();
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(123);
            System.out.println("Listening for a connection");
            clientSocket = serverSocket.accept();
            System.out.println("Received a connection");

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // TODO: handle grep if the input line is the query
                LogSearcher logSearcher = new LogSearcher();
                List<String> result = logSearcher.findLog(inputLine);
                out.println(result);
                out.flush();
                System.out.println("in server while " + inputLine);
            }
            System.out.println("Connection closed");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopServer() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}