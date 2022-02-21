package exec;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class Main {
    private static final int FILE_QUEUE_SIZE = 10;
    private static final Path DUMMY = Path.of("");
    private static final BlockingQueue<Path> queue =
            new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main(String[] args) {
        boolean flag = false;
        var in = new Scanner(System.in);
        while(!flag) {
            System.out.print("Enter base directory: ");
            String directory = in.nextLine();
            System.out.print("Enter dest directory: ");
            String destUrl = in.nextLine();

            if(!check(directory, destUrl)){
                continue;
            }
            task(directory, destUrl);
            flag = true;
        }
    }

    public static void task(String directory, String destUrl) {
        try {

            Path destDirectory = createDirectory(destUrl);


            Runnable enumerator = () -> {
                try {
                    System.out.println("Start enumerator");
                    enumerate(Path.of(directory));
                    queue.put(DUMMY);
                    System.out.println("End enumerator");
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            };

            var th = new Thread(enumerator);
            th.start();

            ExecutorService pool = Executors.newFixedThreadPool(4);
            ArrayList<Future> futures = new ArrayList<>();
            ArrayList<Path> fileNames = new ArrayList<>();


            var done = false;
            while(!done) {
                Path file = queue.take();
                if(file == DUMMY) {
                    queue.put(file);
                    done = true;
                }
                else {
                    fileNames.add(file.getFileName());
                    futures.add(pool.submit(new SortCallable(file)));
                }
            }

            try {
                for(int i=0; i<fileNames.size(); i++){
                    writeInFile(destDirectory, fileNames.get(i), (ArrayList<String>) (futures.get(i)).get());
                }
                pool.shutdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("done");
    }

    public static boolean check(String directory, String  destUrl) {
        if(Objects.equals(directory, destUrl)) {
            System.out.println("Error. Directories are the same.");
            return false;
        }
        else if(!Files.exists(Paths.get(directory))) {
            System.out.println("Error. Base directory wasn't found.");
            return false;
        }
        return true;
    }

    public static Path createDirectory(String destUrl) throws IOException, InterruptedException {
        if(Files.exists(Paths.get(destUrl))) {
            deleteAllFiles(Paths.get(destUrl));
            Files.delete(Paths.get(destUrl));
        }
        return Files.createDirectory(Paths.get(destUrl));
    }

    public static void deleteAllFiles(Path directory) throws IOException, InterruptedException {
        try (Stream<Path> children = Files.list(directory)) {
            for (Path child: children.toList()){
                Files.delete(child);
            }
        }
    }

    public static void enumerate(Path directory) throws IOException, InterruptedException {
        try (Stream<Path> children = Files.list(directory)) {
            for (Path child: children.toList()){
                if (Files.isDirectory(child)) {
                    Runnable enumerator = () -> {
                        try {
                            enumerate(child);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    };
                    System.out.println("Start thread in new directory");
                    var th = new Thread(enumerator);
                    th.start();
                    th.join();
                }
                else {
                    queue.put(child);
                    System.out.println("found file: " + child);
                }
            }
        }
    }

    public static void writeInFile(Path destDirectory, Path p, ArrayList<String> content) throws IOException {
        System.out.println("Write in file: " + destDirectory + "\\" + p);
        Path destFile = Files.createFile(Paths.get(destDirectory + "\\" + p));
        FileWriter writer = new FileWriter(String.valueOf(destFile), false);
        for(String c: content) {
            writer.write(c + '\n');
        }
        writer.flush();
        System.out.println("Write in file: " + destDirectory + "\\" + p + " completed");
    }
}
