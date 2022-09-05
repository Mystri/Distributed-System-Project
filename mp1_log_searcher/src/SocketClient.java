import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketClient {
    private PrintStream out;
    private BufferedReader in;
    Socket socket;
    private String serverIp;

    public SocketClient(String serverIp) {
        this.serverIp = serverIp;
    }

    public void start() {
        System.out.println("Loading contents of URL: " + serverIp);

        try {
            socket = new Socket(serverIp, 123);
            out = new PrintStream(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> sendMessage(String message) {
        out.println(message);
        List<String> resp = new ArrayList<>();
        try {
            String r = in.readLine();
            // We will know how many lines to read based on this information.
            int matchedLogsCount = Integer.parseInt(r);
            for (int i = 0; i < matchedLogsCount; i++) {
                r = in.readLine();
                resp.add(r);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resp;
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
