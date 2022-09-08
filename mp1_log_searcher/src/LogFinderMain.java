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
                long start = System.currentTimeMillis();
                List<String> resultLogs = new ArrayList<>();
                int totalCount = 0;
                for (SocketClient client : clients) {
                    List<String> singleClientResult = client.sendMessage(query);
                    resultLogs.addAll(singleClientResult);
                    if (shouldReturnCount(query)) {
                        // When the user wants the count, we will also need to calculate the total count.
                        // The line will have the format fileLocation:lineCount
                        for (String res: singleClientResult) {
                            String[] splitedResponse = res.split(":");
                            try {
                                int singleCount = Integer.parseInt(splitedResponse[splitedResponse.length - 1]);
                                totalCount += singleCount;
                            } catch (Exception e) {
                                // In case the calculation failed, we will choose to not add the total count.
                            }
                        }
                    }
                }
                for (String logs : resultLogs) {
                    System.out.println(logs);
                }
                if (shouldReturnCount(query)) {
                    System.out.println("Total number for the matching lines is: " + totalCount);
                }
                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;
                System.out.println("Total time used for this command is " + timeElapsed + " milliseconds");
            } else {
                System.out.println("Unexpected Query");
            }
        }
        // Exit by closing the terminal or pressing ctrl+c. So we don't need the line below.
        // scanner.close();
    }

    private static boolean shouldReturnCount(String message) {
        String[] splitedMessage = message.split(" ");
        if (splitedMessage.length >= 2) {
            return splitedMessage[1].equals("-c") || splitedMessage[1].equals("-Ec");
        }
        return false;
    }
}
