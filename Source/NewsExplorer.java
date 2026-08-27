import java.io.*;

class NewsExplorer {

    static String[] news = new String[100];
    static int count = 0;

    static void readFile(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {
            if (!line.isEmpty())
                news[count++] = line;
        }

        br.close();
    }

    static boolean zSearch(String text, String pattern) {
        String s = pattern + "$" + text;
        int[] z = new int[s.length()];
        int l = 0, r = 0;

        for (int i = 1; i < s.length(); i++) {

            if (i <= r)
                z[i] = Math.min(r - i + 1, z[i - l]);

            while (i + z[i] < s.length() &&
                   s.charAt(z[i]) == s.charAt(i + z[i]))
                z[i]++;

            if (i + z[i] - 1 > r) {
                l = i;
                r = i + z[i] - 1;
            }

            if (z[i] == pattern.length())
                return true;
        }

        return false;
    }

    static void searchNews(String key) {
        for (int i = 0; i < count; i++)
            if (zSearch(news[i].toLowerCase(), key.toLowerCase()))
                System.out.println(news[i]);
    }

    public static void main(String[] args) throws IOException {

        File folder = new File(".");
        File[] files = folder.listFiles();

        // Read all .txt files automatically
        for (File f : files) {
            if (f.getName().endsWith(".txt"))
                readFile(f.getName());
        }

        BufferedReader in =
            new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter keyword: ");
        searchNews(in.readLine());
    }
}