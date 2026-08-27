# NewsExplorer – News Keyword Search Using Z Algorithm

## 1. Project Overview

**NewsExplorer** is a Java-based program that searches for a given keyword or pattern inside multiple news text files. The program automatically detects all `.txt` files in the current folder, reads their contents line by line, and stores the news articles in memory.

When the user enters a keyword, the program searches through every stored news line using the **Z Algorithm**, which is an efficient string-matching algorithm. If the keyword is found in a news line, that complete line is displayed.

## 2. Objectives

* Automatically read news from `.txt` files.
* Store multiple news lines in an array.
* Allow the user to enter a keyword for searching.
* Use the **Z Algorithm** for pattern matching.
* Perform case-insensitive searching.
* Display all news lines containing the given keyword.

## 3. Technologies Used

* **Programming Language:** Java
* **File Handling:** `File`, `FileReader`, `BufferedReader`
* **Input Handling:** `InputStreamReader`, `BufferedReader`
* **Algorithm:** Z Algorithm
* **Data Structure:** String Array

## 4. Program Structure

The program contains the following important methods:

### `readFile(String file)`

This method reads a `.txt` file line by line.

```java
static void readFile(String file) throws IOException
```

### Implementation

* Creates a `FileReader` for the specified file.
* Uses `BufferedReader` to read each line.
* Ignores empty lines.
* Stores non-empty lines in the `news` array.
* Increases the `count` variable after storing each line.

The `count` variable keeps track of how many news lines have been stored.

---

## 5. Z Algorithm Implementation

The main searching operation is performed by:

```java
static boolean zSearch(String text, String pattern)
```

The Z Algorithm is a string pattern-matching algorithm.

The program creates a combined string:

```java
String s = pattern + "$" + text;
```

Here:

* `pattern` is the keyword entered by the user.
* `$` is a separator that prevents unwanted matching between the pattern and text.
* `text` is the news line being searched.

For example:

```text
Pattern = java
Text = Learn java programming
```

The combined string becomes:

```text
java$Learn java programming
```

The program calculates the **Z-array** for this combined string.

If any value in the Z-array becomes equal to the length of the pattern, it means the complete pattern has been found.

Therefore:

```java
if (z[i] == pattern.length())
    return true;
```

returns `true` when the keyword is found.

## 6. Z-Array

The Z-array stores the length of the substring starting at each position that matches the prefix of the combined string.

The implementation maintains a window using:

```java
int l = 0, r = 0;
```

where:

* `l` = left boundary of the current Z-box.
* `r` = right boundary of the current Z-box.

This helps avoid unnecessary character comparisons and makes the searching process efficient.

## 7. Searching News

The method:

```java
static void searchNews(String key)
```

searches the keyword in every stored news line.

```java
for (int i = 0; i < count; i++)
    if (zSearch(news[i].toLowerCase(), key.toLowerCase()))
        System.out.println(news[i]);
```

Both the news line and keyword are converted to lowercase:

```java
news[i].toLowerCase()
key.toLowerCase()
```

This makes the search **case-insensitive**.

For example, searching for:

```text
Java
```

can match:

```text
java
JAVA
Java
```

## 8. Automatic File Detection

Inside the `main()` method, the program checks the current directory:

```java
File folder = new File(".");
File[] files = folder.listFiles();
```

The `"."` represents the current directory.

The program then checks every file:

```java
for (File f : files) {
    if (f.getName().endsWith(".txt"))
        readFile(f.getName());
}
```

Only files ending with `.txt` are read.

This means the user does not need to manually provide every file name.

## 9. User Input

The program takes the keyword from the user using:

```java
BufferedReader in =
    new BufferedReader(new InputStreamReader(System.in));
```

The keyword is obtained using:

```java
searchNews(in.readLine());
```

The user only needs to enter the keyword they want to search for.

## 10. Complete Program Flow

The program works in the following sequence:

1. Start the Java program.
2. Find all files in the current directory.
3. Check whether each file has the `.txt` extension.
4. Read the contents of each `.txt` file.
5. Store non-empty news lines in the `news` array.
6. Ask the user to enter a keyword.
7. Convert the keyword and news text to lowercase.
8. Apply the Z Algorithm to each news line.
9. Check whether the keyword exists.
10. Print every news line containing the keyword.

## 11. Example Input Files

Suppose the folder contains:

```text
news1.txt
news2.txt
news3.txt
```

### `news1.txt`

```text
Java is widely used for software development.
Artificial intelligence is changing technology.
```

### `news2.txt`

```text
Technology companies are investing in AI.
Java programming is popular among students.
```

If the user enters:

```text
java
```

## 12. Example Output

```text
Enter keyword: java
Java is widely used for software development.
Java programming is popular among students.
```

The program prints the original news lines, even though the search itself is performed using lowercase text.

## 13. Algorithm Used

### Z Algorithm

The **Z Algorithm** is used for finding occurrences of a pattern in a string.

For a string of length `n`, the Z-array can be constructed in:

**Time Complexity: O(n)**

For each news line, the combined string has approximately:

```text
pattern length + news line length
```

characters.

Therefore, the search for one news line takes:

**O(P + T)**

where:

* `P` = length of the keyword.
* `T` = length of the news line.

If there are multiple news lines, the total searching time depends on the combined size of all news data.

## 14. Space Complexity

The program uses:

* `news[100]` to store up to 100 news lines.
* A Z-array for the combined pattern and text.

The Z-array requires:

**O(P + T)**

additional space for each search.

## 15. Advantages

* Automatically reads all `.txt` files.
* No need to manually specify file names.
* Uses an efficient pattern-matching algorithm.
* Search is case-insensitive.
* Simple and easy to extend.
* Can handle multiple news files.

## 16. Limitations

* The `news` array can store only 100 lines.
* Only `.txt` files in the current directory are automatically considered.
* The program searches individual lines rather than treating an entire file as one article.
* Empty lines are ignored.
* The program does not display the file name from which a matching line came.

## 17. Possible Future Improvements

The program can be improved by:

* Using `ArrayList<String>` instead of a fixed-size array.
* Displaying the file name along with matching news.
* Supporting searches for multiple keywords.
* Adding a graphical user interface.
* Ranking news based on keyword frequency.
* Highlighting the matching keyword.
* Supporting more file formats.
* Adding a search history feature.

## 18. Conclusion

**NewsExplorer** demonstrates how Java file handling and string algorithms can be combined to build a simple news-search system. The program automatically loads news from text files and uses the **Z Algorithm** to efficiently search for a user-provided keyword. It provides a practical example of file handling, arrays, string manipulation, user input, and algorithm implementation in Java.

