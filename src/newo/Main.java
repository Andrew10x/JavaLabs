package newo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private final static ArrayList<String> content = new ArrayList<>();
    private static final Path file = Paths.get("D:\\test2\\d.txt");

    public static void main(String[] args) throws IOException {
        var in = new Scanner(file, StandardCharsets.UTF_8);
        while (in.hasNextLine()) {
            content.add(in.next());
        }
        System.out.println("Sort file: " + file.getFileName());
        content.sort(String.CASE_INSENSITIVE_ORDER);
    }
}
