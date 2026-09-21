import java.io.*;

public class NewsExplorer {

    static String[] news = new String[50];
    static int count = 0;

    // ================= MODULE 1 =================

    static void loadNews() throws IOException {
        BufferedReader br =
            new BufferedReader(new FileReader("news.txt"));

        String line;

        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty())
                news[count++] = line;
        }

        br.close();
    }

    static void showNews() {
        System.out.println("\n--- NEWS ---");

        for (int i = 0; i < count; i++)
            System.out.println((i + 1) + ". " + news[i]);
    }


    // ================= MODULE 2 =================
    // KMP

    static int[] lps(String p) {
        int[] a = new int[p.length()];
        int j = 0;

        for (int i = 1; i < p.length();) {
            if (p.charAt(i) == p.charAt(j)) {
                a[i++] = ++j;
            }
            else if (j > 0) {
                j = a[j - 1];
            }
            else {
                i++;
            }
        }

        return a;
    }

    static boolean kmp(String text, String pattern) {

        int[] a = lps(pattern);

        int i = 0, j = 0;

        while (i < text.length()) {

            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == pattern.length())
                    return true;
            }
            else if (j > 0) {
                j = a[j - 1];
            }
            else {
                i++;
            }
        }

        return false;
    }


    // ================= MODULE 2 =================
    // Z FUNCTION

    static boolean zSearch(String text, String pattern) {

        String s = pattern + "$" + text;
        int[] z = new int[s.length()];

        int left = 0, right = 0;

        for (int i = 1; i < s.length(); i++) {

            if (i <= right)
                z[i] =
                    Math.min(right - i + 1,
                             z[i - left]);

            while (i + z[i] < s.length() &&
                   s.charAt(z[i]) ==
                   s.charAt(i + z[i])) {
                z[i]++;
            }

            if (i + z[i] - 1 > right) {
                left = i;
                right = i + z[i] - 1;
            }

            if (z[i] == pattern.length())
                return true;
        }

        return false;
    }


    // ================= MODULE 3 =================
    // EDIT DISTANCE
    // Wagner-Fischer DP

    static int editDistance(String a, String b) {

        int[][] dp =
            new int[a.length() + 1]
                    [b.length() + 1];

        for (int i = 0; i <= a.length(); i++)
            dp[i][0] = i;

        for (int j = 0; j <= b.length(); j++)
            dp[0][j] = j;

        for (int i = 1; i <= a.length(); i++) {

            for (int j = 1; j <= b.length(); j++) {

                if (a.charAt(i - 1) ==
                    b.charAt(j - 1)) {

                    dp[i][j] =
                        dp[i - 1][j - 1];

                }
                else {

                    dp[i][j] =
                        1 + Math.min(
                            dp[i - 1][j - 1],
                            Math.min(
                                dp[i - 1][j],
                                dp[i][j - 1]
                            )
                        );
                }
            }
        }

        return dp[a.length()][b.length()];
    }


    // ================= MODULE 3 =================
    // LCS

    static int lcs(String a, String b) {

        int[][] dp =
            new int[a.length() + 1]
                    [b.length() + 1];

        for (int i = 1; i <= a.length(); i++) {

            for (int j = 1; j <= b.length(); j++) {

                if (a.charAt(i - 1) ==
                    b.charAt(j - 1)) {

                    dp[i][j] =
                        dp[i - 1][j - 1] + 1;

                }
                else {

                    dp[i][j] =
                        Math.max(
                            dp[i - 1][j],
                            dp[i][j - 1]
                        );
                }
            }
        }

        return dp[a.length()][b.length()];
    }


    // ================= SEARCH =================

    static void search(String keyword, int type) {

        keyword = keyword.toLowerCase();

        System.out.println("\n--- SEARCH RESULTS ---");

        for (int i = 0; i < count; i++) {

            String text =
                news[i].toLowerCase();

            boolean found;

            if (type == 1)
                found = kmp(text, keyword);
            else
                found = zSearch(text, keyword);

            if (found)
                System.out.println(news[i]);
        }
    }


    // ================= FUZZY SEARCH =================

    static void fuzzySearch(String keyword) {

        keyword = keyword.toLowerCase();

        System.out.println("\n--- FUZZY SEARCH ---");

        for (int i = 0; i < count; i++) {

            String[] words =
                news[i].toLowerCase().split(" ");

            int best = 100;

            for (int j = 0; j < words.length; j++) {

                String word =
                    words[j].replace(".", "")
                            .replace(",", "");

                int d =
                    editDistance(
                        keyword,
                        word
                    );

                if (d < best)
                    best = d;
            }

            if (best <= 2)
                System.out.println(news[i]);
        }
    }


    // ================= COMPARE NEWS =================

    static void compareNews() throws IOException {

        BufferedReader br =
            new BufferedReader(
                new InputStreamReader(System.in));

        System.out.print("First article number: ");
        int a = Integer.parseInt(br.readLine());

        System.out.print("Second article number: ");
        int b = Integer.parseInt(br.readLine());

        String x =
            news[a - 1].toLowerCase();

        String y =
            news[b - 1].toLowerCase();

        System.out.println(
            "\nEdit Distance: "
            + editDistance(x, y));

        System.out.println(
            "LCS Length: "
            + lcs(x, y));
    }


    // ================= MAIN =================

    public static void main(String[] args)
            throws Exception {

        loadNews();
        
        BufferedReader br =
            new BufferedReader(
                new InputStreamReader(System.in));

        while (true) {

            System.out.println(
                "\n===== NEWS EXPLORER =====");

            System.out.println("1. Show News");
            System.out.println("2. KMP Search");
            System.out.println("3. Z-Function Search");
            System.out.println("4. Fuzzy Search");
            System.out.println("5. Compare News");
            System.out.println("6. Exit");

            System.out.print("Choice: ");

            int choice =
                Integer.parseInt(br.readLine());

            if (choice == 1) {

                showNews();

            }
            else if (choice == 2) {

                System.out.print("Keyword: ");
                search(br.readLine(), 1);

            }
            else if (choice == 3) {

                System.out.print("Keyword: ");
                search(br.readLine(), 2);

            }
            else if (choice == 4) {

                System.out.print("Keyword: ");
                fuzzySearch(br.readLine());

            }
            else if (choice == 5) {

                compareNews();

            }
            else if (choice == 6) {

                break;

            }
            else {

                System.out.println("Invalid choice.");
            }
        }
    }
}