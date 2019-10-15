public class Main {
    public static void main(String[] args) {

        FileIndexer fh = new FileIndexer();
//        fh.setSearchScope("D:\\GIT\\cloud-repository-help\\Content\\Administrators_Group.htm");
        fh.setSearchScope("D:\\GIT\\cloud-repository-help\\Content");
        System.out.println(fh.search("get"));
    }
}
