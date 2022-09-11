import org.junit.jupiter.api.*;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Unit tests for querying with grep search on local files.
 */
public class UnitTests {

    private static String getLogsGeneratePath(String fileName) {
        return "./logFiles/" + (fileName.length() > 0 ? "__test_" + fileName : fileName);
    }

    private static QueryHandler queryHandler;


    @BeforeAll
    public static void setUp() {
        queryHandler = new QueryHandler();
    }

    @AfterEach
    public void tearDown() {
        for (File file : new File(getLogsGeneratePath("")).listFiles()) {
            if (file.getName().startsWith("__test_")) file.delete();
        }
    }
    
    @Test
    public void testLocalQueryResults_noOccurence() {
        LogGenerator.generate(getLogsGeneratePath("test1"), "aaaa", 1, 1);
        List<String> queryResults = queryHandler.getQueryResults("grep -c test", "__test_test1.log");
        Assertions.assertEquals(1, queryResults.size());

        String result = queryResults.get(0);
        Assertions.assertTrue(result.startsWith("__test_test1.log"));
        String[] resultSplit = result.split(":");
        Assertions.assertEquals(0, Integer.parseInt(resultSplit[1]));
    }

    @Test
    public void testLocalQueryResults_singleFileSingleResult_rowCount() {
        LogGenerator.generate(getLogsGeneratePath("test1"), "test", 1, 1);
        List<String> queryResults = queryHandler.getQueryResults("grep -c test", "__test_test1.log");
        Assertions.assertEquals(1, queryResults.size());

        String result = queryResults.get(0);
        Assertions.assertTrue(result.startsWith("__test_test1.log"));
        String[] resultSplit = result.split(":");
        Assertions.assertEquals(1, Integer.parseInt(resultSplit[1]));

    }

    @Test
    public void testLocalQueryResults_singleFileSingleResult_rowCount_regex() {
        LogGenerator.generate(getLogsGeneratePath("test1"), "test", 1, 1);
        List<String> queryResults = queryHandler.getQueryResults("grep -Ec ^te.t", "__test_test1.log");
        Assertions.assertEquals(1, queryResults.size());

        String result = queryResults.get(0);
        Assertions.assertTrue(result.startsWith("__test_test1.log"));
        String[] resultSplit = result.split(":");
        Assertions.assertEquals(1, Integer.parseInt(resultSplit[1]));

    }

    @Test
    public void testLocalQueryResults_singleFileSingleResult_eachLine_regex() {
        LogGenerator.generate(getLogsGeneratePath("test1"), "keyword", 1, 1);
        List<String> queryResults = queryHandler.getQueryResults("grep -E ^key..rd", "__test_test1.log");
        Assertions.assertEquals(1, queryResults.size());

        String result = queryResults.get(0);
        Assertions.assertTrue(result.startsWith("__test_test1.log"));
        Assertions.assertTrue(result.contains("keyword"));
    }

    @Test
    public void testLocalQueryResults_singleFileSingleResult_eachLine() {
        LogGenerator.generate(getLogsGeneratePath("test1"), "keyword", 1, 1);
        List<String> queryResults = queryHandler.getQueryResults("grep keyword", "__test_test1.log");
        Assertions.assertEquals(1, queryResults.size());

        String result = queryResults.get(0);
        Assertions.assertTrue(result.startsWith("__test_test1.log"));
        Assertions.assertTrue(result.contains("keyword"));
    }

    @Test
    public void testLocalQueryResults_singleFileMultipleResult_rowCount() {
        LogGenerator.generate(getLogsGeneratePath("test1"), "test", 50, 100);
        List<String> queryResults = queryHandler.getQueryResults("grep -c test", "__test_test1.log");

        String result = queryResults.get(0);
        Assertions.assertTrue(result.startsWith("__test_test1.log"));
        String[] resultSplit = result.split(":");
        Assertions.assertEquals(50, Integer.parseInt(resultSplit[1]));
    }

    @Test
    public void testLocalQueryResults_multipleFileMultipleResult_rowCount() {
        for (int i = 0; i < 10 ; i++) {
            LogGenerator.generate(getLogsGeneratePath("test" + i), "test", 50, 100);
        }
        List<String> queryResults = queryHandler.getQueryResults("grep -c test", null);

        for (int i = 0; i < queryResults.size(); i++) {
            String result = queryResults.get(i);

            Assertions.assertTrue(result.contains("__test_test" + i + ".log"));
            String[] resultSplit = result.split(":");
            Assertions.assertEquals(50, Integer.parseInt(resultSplit[1]));
        }
    }

    @Test
    public void testLocalQueryResults_multipleFileMultipleResult_rowCount_regex() {
        for (int i = 0; i < 10 ; i++) {
            LogGenerator.generate(getLogsGeneratePath("test" + i), "test", 50, 100);
        }
        List<String> queryResults = queryHandler.getQueryResults("grep -Ec ^te.t", null);

        for (int i = 0; i < queryResults.size(); i++) {
            String result = queryResults.get(i);

            Assertions.assertTrue(result.contains("__test_test" + i + ".log"));
            String[] resultSplit = result.split(":");
            Assertions.assertEquals(50, Integer.parseInt(resultSplit[1]));
        }
    }


}