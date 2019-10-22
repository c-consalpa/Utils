;import java.io.*;
import java.util.*;


class FileIndexer {
    private File searchArea;

    FileIndexer() {

    }

    void setSearchScope(String src) {
        this.searchArea = new File(src);
    }

    List<SearchResult> search(String pattern) {
        List<SearchResult> result;

        if (searchArea.isDirectory()) {
            result = searchInDirectory(searchArea, pattern);
        } else {
            result = searchInFile(searchArea, pattern);
        }
        return result;
    }

    private List<SearchResult> searchInFile(File f, String str)  {
        List<SearchResult> res = new ArrayList<>();
        String line = "";
        int lineNum = 1;


        try (BufferedReader bfReader = new BufferedReader(new FileReader(f));) {
            while (null != (line = bfReader.readLine())) {
                Map<Integer, String> occurrences = searchOccurrences(line, str);
                if (occurrences.size() > 0) {
                    for (Integer i: occurrences.keySet()) {
                        String occurrenceString = occurrences.get(i);
                        res.add(new SearchResult(str, f, lineNum, i, occurrenceString));
                    }

                }
                lineNum++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    private List<SearchResult> searchInDirectory(File searchArea, String str) {
        List<SearchResult> res = new ArrayList<>();
        List<File> dirContents = traverseDir(this.searchArea);
        for (File f : dirContents) {
            res.addAll(searchInFile(f, str));
        }
        return res;
    }

    private Map<Integer, String> searchOccurrences(String line, String pattern) {
        Map<Integer, String> occurrenceMap = new LinkedHashMap<>();
        if (line.isEmpty() || line.length() < pattern.length()) return occurrenceMap;

        int matchIndex = 0;
        int cnt = 0;
//search for string occurrance in a line
        while ((matchIndex = line.indexOf(pattern, matchIndex))!= -1) {

            String surroundingStr = getSurroundingString(line, pattern, matchIndex);
//            Once we find "abc" => move 3 indexes forward;
            occurrenceMap.put(matchIndex, surroundingStr);
            matchIndex += pattern.length() - 1;
            cnt++;


        }
        return occurrenceMap;
    }

    private static String getSurroundingString(String line, String pattern, int matchIndex) {
//        The method returns the pattern with nearby surrounding chars, e.g.: abcd*pattern*zyz...
        StringBuilder sb = new StringBuilder();
//      defines how much chars to show before and after the pattern
        int bounds = 20;

//        get chars before pattern
        int charsbefore = matchIndex;
        if (charsbefore > bounds) {
            sb.append("...");
            sb.append(line, (matchIndex - bounds), matchIndex);
        } else {
            sb.append(line, 0, charsbefore);
        }

//add pattern itself
        sb.append("*" + pattern + "*");

//      get chars after the pattern
        int charsafter = line.length() - (matchIndex + pattern.length());
        if (charsafter <= bounds) {
            sb.append(line, (matchIndex + pattern.length()), line.length());
        } else {
            sb.append(line, (matchIndex + pattern.length()), (matchIndex + pattern.length() + bounds));
            sb.append("...");
        }

        return sb.toString();
    }

    private List<File> traverseDir(File startDir) {
        List<File> result = new ArrayList<>();
        File[] files = startDir.listFiles();
        if (files.length == 0) {
//            break the loop if directory is empty
            return null;
        }

        List<File> filesInDirectory = new ArrayList<>();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                List<File> tmp = traverseDir(files[i]);
                if (tmp != null) result.addAll(tmp);
            } else {
                filesInDirectory.add(files[i]);
            }
        }
//        harvesitng files accumulated in *for* loop:
        result.addAll(filesInDirectory);
        return result;
    }

}
