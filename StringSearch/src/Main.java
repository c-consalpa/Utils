import java.util.List;
import java.util.Scanner;

public class Main {
//    TODO 1. Implement file type selection by ext;
//    TODO 2. JARify app;
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
//            String location = "D:\\GIT\\eam-xe-xes-D:\GIT\eam-xe-xes-help-centerhelp-center\\Content\\Edifecs_Library", searchString = "wait";

            System.out.println("Enter location:");
            String location = scanner.nextLine();
            System.out.println(location);
            System.out.println("Enter search string:");
            String searchString = scanner.nextLine();


            FileIndexer fh = new FileIndexer();
            fh.setSearchScope(location);
            List<SearchResult> results = fh.search(searchString);
            for (SearchResult r: results) {
                System.out.println(r);
            }
    }
}
