package exec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class SortCallable implements Callable<ArrayList<String>> {
    private final ArrayList<String> content = new ArrayList<>();
    private final Path file;

    public SortCallable(Path file) {
        this.file = file;
    }
    public ArrayList<String> call() throws IOException {
        try (var in = new Scanner(file, StandardCharsets.UTF_8)){
            while (in.hasNextLine()) {
                content.add(in.next());
            }
            System.out.println("Sort file: " + file.getFileName());
            content.sort(String.CASE_INSENSITIVE_ORDER);
            System.out.println("File " + file.getFileName() + " was sorted");
            return content;
        }
    }
}