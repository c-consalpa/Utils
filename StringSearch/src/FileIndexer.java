;import java.io.*;
import java.util.*;


public class FileIndexer {
    private File searchArea;

    public FileIndexer() {

    }

    public void setSearchScope(String src) {
        this.searchArea = new File(src);
    }

    public List<SearchResult> search(String pattern) {
        List<SearchResult> result;

        if (searchArea.isDirectory()) {
            result = searchInDirectory(searchArea, pattern);
        } else {
            result = searchInFile(searchArea, pattern);
        }
        return result;
    }

    public List<SearchResult> searchInFile(File f, String str)  {
        List<SearchResult> res = new ArrayList<>();
        String line = "";
        int lineNum = 1;

        Map<Integer, Integer> stringOccurence = new HashMap<>();

        try (BufferedReader bfReader = new BufferedReader(new FileReader(f));) {
            while (null != (line = bfReader.readLine())) {
                Map<Integer, String> occurrences = searchOccurrences(line, str);
                if (occurrences.size() > 0) {
                    for (Integer i: occurrences.keySet()) {
                        res.add(new SearchResult(str, f, lineNum));
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



    public List<SearchResult> searchInDirectory(File searchArea, String str) {
        List<SearchResult> res = new ArrayList<>();
        List<File> dirContents = traverseDir(this.searchArea);
        for (File f : dirContents) {
            res.addAll(searchInFile(f, str));
        }
        return res;
    }

    private Map<Integer, String> searchOccurrences(String line, String pattern) {
        if (line.isEmpty() || line.length() < pattern.length()) return null;
        Map<Integer, String> occurrenceMap = new LinkedHashMap<>();
        int matchIndex = 0;
        int cnt = 0;
//search for string occurrance in a line
        while ((matchIndex = line.indexOf(pattern, matchIndex))!= -1) {

            String surroundingStr = getSurroundingString(line.substring(matchIndex), line.length(), pattern);
//            Once we find "abc" => move 3 indexes forward;
            occurrenceMap.put(matchIndex, surroundingStr);
            matchIndex += pattern.length() - 1;
            cnt++;


        }
        return occurrenceMap;
    }

    private String getSurroundingString(String substring, int length, String pattern) {
        return "testSurrString";
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
