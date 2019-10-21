import java.io.File;

public class SearchResult {
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

    }


    @Override
    public String toString() {
        String raw =
                "Found: '"  + searchPattern + "'; "     +
                "File: "    + foundInFile.getName()     + "; " +
                "Line: "    + foundInLine               + ":"  +
                            + occurrenceIndex           + "; " +
                 "("        + occurrenceString          + "); ";

        return raw;
//TODO implement formatting;
    }

}
