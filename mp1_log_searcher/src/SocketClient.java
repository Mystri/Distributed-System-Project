import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

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

    public String sendMessage(String message) {
        out.println(message);
        String resp;
        try {
            resp = in.readLine();
            System.out.println("resp " + resp);
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
