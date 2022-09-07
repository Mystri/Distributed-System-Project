import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Client class. It will connect to a server, send a message when the method is called,
 * and print out the responses.
 */
public class SocketClient {
    private PrintStream out;
    private BufferedReader in;
    Socket socket;
    private final String serverIp;

    public SocketClient(String serverIp) {
        this.serverIp = serverIp;
    }

    public boolean start() {
        System.out.println("Loading contents of URL: " + serverIp);
        try {
            socket = new Socket(serverIp, 8001);
            out = new PrintStream(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (Exception e) {
            // We don't print stack trace to keep the console clean.
            System.out.println("Connect to " + serverIp + " failed");
            return false;
        }
    }

    public List<String> sendMessage(String message) {
        out.println(message);
        List<String> resp = new ArrayList<>();
        try {
            String r = in.readLine();
            // r will be null if the server is turned off.
            if (r != null) {
                // We will know how many lines to read based on this information.
                int outputLineCount = Integer.parseInt(r);
                for (int i = 0; i < outputLineCount; i++) {
                    r = in.readLine();
                    resp.add(r);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }
}
