import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            String location = "D:\\java\\FileUtils\\New Text Document.txt",
                    searchString = "abc";


//                System.out.println("Enter location:");
//                location = scanner.nextLine();
//                System.out.println(location);
//                System.out.println("Enter search string:");
//                searchString = scanner.nextLine();






                FileIndexer fh = new FileIndexer();
                fh.setSearchScope(location);
                List<SearchResult> results = fh.search(searchString);
        for (SearchResult r:
             results) {
            System.out.println(r);
        }





    }
}
