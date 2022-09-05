import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class SocketServer extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public SocketServer(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                // TODO: handle grep if the input line is the query
                LogSearcher logSearcher = new LogSearcher();
                LogResult logResult = logSearcher.findLog(inputLine, false);
                out.println(logResult.matchedLogsCount);
                for (String matchedLog: logResult.matchedLogs) {
                    out.println(matchedLog);
                }
                System.out.println("before flush");
                out.flush();
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