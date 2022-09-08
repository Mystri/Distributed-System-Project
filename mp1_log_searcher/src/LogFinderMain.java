import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class as a client. This class will handle the input
 * command and start multiple clients that connect to all the servers.
 */
public class LogFinderMain {
    public static void main(String[] args) {
        // List of all the VMs
        String[] ips = {
                "fa22-cs425-0501.cs.illinois.edu",
                "fa22-cs425-0502.cs.illinois.edu",
                "fa22-cs425-0503.cs.illinois.edu",
                "fa22-cs425-0504.cs.illinois.edu",
                "fa22-cs425-0505.cs.illinois.edu",
                "fa22-cs425-0506.cs.illinois.edu",
                "fa22-cs425-0507.cs.illinois.edu",
                "fa22-cs425-0508.cs.illinois.edu",
                "fa22-cs425-0509.cs.illinois.edu",
                "fa22-cs425-0510.cs.illinois.edu"
        };

        List<SocketClient> clients = new ArrayList<>();
        for (String ip : ips) {
            SocketClient client = new SocketClient(ip);
            boolean isSucceeded = client.start();
            if (isSucceeded) {
                clients.add(client);
            }
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String query = scanner.nextLine();
            if (query.startsWith("grep ")) {
                List<String> resultLogs = new ArrayList<>();
                for (SocketClient client : clients) {
                    List<String> singleClientResult = client.sendMessage(query);
                    resultLogs.addAll(singleClientResult);
                }
                for (String logs : resultLogs) {
                    System.out.println(logs);
                }
            } else {
                System.out.println("Unexpected Query");
            }
        }
        // Exit by closing the terminal or pressing ctrl+c. So we don't need the line below.
        // scanner.close();
    }
}
