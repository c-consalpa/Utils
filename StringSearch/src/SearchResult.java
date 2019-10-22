import java.io.File;
import java.text.Normalizer;
import java.util.Formatter;

public class SearchResult {
    private static int cnt = 0;
    private int searchResultID;
    private File    foundInFile;
    private int     foundInLine;
    private String  searchPattern;
    private String  occurrenceString;
    private int     occurrenceIndex;

    public SearchResult(String str, File f, int lineNum, Integer occurrenceIndex, String occurrenceString) {
        this.foundInFile = f;
        this.foundInLine = lineNum;
        searchPattern = str;
        this.occurrenceString = occurrenceString;
        this.occurrenceIndex = occurrenceIndex;
        searchResultID = ++cnt;
    }


    @Override
    public String toString() {
    StringBuilder sb = new StringBuilder();
    Formatter fmt = new Formatter(sb);
    sb.append("[" + searchResultID + "] ");
    fmt.format("File: %s; ", foundInFile.getName());
    fmt.format("line %1$d at %2$d:%n", foundInLine, occurrenceIndex);
    fmt.format("(%30s)%n", occurrenceString);
    fmt.format("%s", "*********************************************");

    return sb.toString();


    }

}
