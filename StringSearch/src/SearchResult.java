import java.io.File;

public class SearchResult {
    private File foundInFile;
    private int foundInLine;
    private String lineContents;
    private String searchPattern;

    public SearchResult(String str, File f, int lineNum) {
        this.foundInFile = f;
        this.foundInLine = lineNum;
        searchPattern = str;
    }


    @Override
    public String toString() {
        return  "Found: '" + searchPattern + "' "   +
                "File: " + foundInFile.getName() + "; " +
                "Line: " + foundInLine + "\n";
    }
}
