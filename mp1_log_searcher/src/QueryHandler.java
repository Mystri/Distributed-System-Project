import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Use a process to run the grep command directly.
 */
public class QueryHandler {
    public List<String> getQueryResults(String inputLine, String singleFilePath) {
        List<String> commandResults = new ArrayList<>();
        try {
            // We need to treat it as shell for command that includes *.
            // https://stackoverflow.com/questions/2111983/java-runtime-getruntime-exec-wildcards
            String[] args = new String[]{"sh", "-c", inputLine};
            ProcessBuilder builder = new ProcessBuilder(args);
            Process p = builder.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            String commandOutput;
            while ((commandOutput = br.readLine()) != null) {
                if (singleFilePath == null) {
                    commandResults.add(commandOutput);
                } else {
                    commandResults.add(singleFilePath + ":" + commandOutput);
                }
            }
            p.waitFor();
            p.destroy();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return commandResults;
    }
}
