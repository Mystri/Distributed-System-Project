import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class as a client. This class will handle the input
 * command and start a client that connect to all the servers.
 */
public class InputProcessor {
    public static void main(String[] args) {
        // TODO: Start a socket for each ip, maybe hardcode all of them
        String[] ips = {"::1"};

        List<SocketClient> clients = new ArrayList<>();
        for (int i = 0; i < ips.length; i++) {
            SocketClient client = new SocketClient(ips[i]);
            clients.add(client);
            client.start();
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("grep query: ");
            String query = scanner.nextLine();
            if (query.equals("exit")) {
                break;
            }
            for (SocketClient client : clients) {
                String result = client.sendMessage(query);
                System.out.println("result is " + result);
            }
        }
        scanner.close();
    }
}
