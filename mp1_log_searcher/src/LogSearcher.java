import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LogSearcher {
    private static final String LOG_DIRECTORY = "./logFiles/";
    private static final String LOG_FILE_EXTENSION = ".log";

    public LogResult findLog(String keyword, Boolean isRegex) {
        List<String> allLogs = readLogFile();
        List<String> matchedLogs = new ArrayList<>();
        int matchedLogsCount = 0;
        for (String line : allLogs) {
            if (isRegex) {
                if (line.matches(keyword)) {
                    matchedLogs.add(line);
                    matchedLogsCount++;
                }
            } else {
                if (line.contains(keyword)) {
                    matchedLogs.add(line);
                    matchedLogsCount++;
                }
            }
        }
        return new LogResult(matchedLogs, matchedLogsCount);
    }

    // Read all files that end with ".log"
    private List<String> readLogFile() {
        List<String> output = new ArrayList<>();
        File[] allFiles = new File(LOG_DIRECTORY).listFiles();
        try {
            for (File file : allFiles) {
                if (file.getName().endsWith(LOG_FILE_EXTENSION)) {
                    Scanner fileReader = new Scanner(file);
                    while (fileReader.hasNextLine()) {
                        output.add(fileReader.nextLine());
                    }
                    fileReader.close();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return output;
    }
}
