import java.util.List;

public class LogResult {
    List<String> matchedLogs;
    int matchedLogsCount;

    public LogResult(List<String> matchedLogs, int matchedLogsCount) {
        this.matchedLogs = matchedLogs;
        this.matchedLogsCount = matchedLogsCount;
    }
}
