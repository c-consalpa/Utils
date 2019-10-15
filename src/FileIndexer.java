;import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
//        Map<stringOccurenceCounte, StringOccurencePosition>
        Map<Integer, Integer> stringOccurence = new HashMap<>();
        try (BufferedReader bfReader = new BufferedReader(new FileReader(f));) {

            while (null != (line = bfReader.readLine())) {
                int occurenceCnt = searchOccurrences(line, str);
                if (occurenceCnt > 0) {
                    res.add(new SearchResult(str, f, lineNum));
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
        for (File f : traverseDir(this.searchArea)) {
            res.addAll(searchInFile(f, str));
        }
        return res;
    }

    private int searchOccurrences(String line, String pattern) {
        if (line.isEmpty() || line.length() < pattern.length()) return 0;

        int matchIndex = 0;
        int cnt = 0;

        while ((matchIndex = line.indexOf(pattern, matchIndex))!= -1) {
            matchIndex++;
            cnt++;
        }
        return cnt;
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
