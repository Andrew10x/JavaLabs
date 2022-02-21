package com.company;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

public class Main {
    private static final int FILE_QUEUE_SIZE = 10;
    private static final int SEARCH_THREADS = 100;
    private static final Path DUMMY = Path.of("");
    private static final BlockingQueue<Path> queue =
             new ArrayBlockingQueue<>(FILE_QUEUE_SIZE);

    public static void main(String[] args) {

        try (var in = new Scanner(System.in)){
            System.out.print("Enter base directory ");
            String directory = in.nextLine();
            String destUrl = "D:\\test2";
            Path destDirectory = createDirectory(destUrl);


            Runnable enumerator = () -> {
                try {
                    enumerate(Path.of(directory));
                    queue.put(DUMMY);
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            };

             var th = new Thread(enumerator);
                     th.start();


            for (int i = 1; i <= SEARCH_THREADS; i++) {
                Runnable sorter = () -> {
                    try {
                        var done = false;
                        while(!done) {
                            Path file = queue.take();
                            if(file == DUMMY) {
                                queue.put(file);
                                done = true;
                            }
                            else {
                                sortAndWriteFile(file, destDirectory);
                            }
                        }
                    } catch (InterruptedException | IOException e) {
                        e.printStackTrace();
                    }
                };
                new Thread(sorter).start();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("done");
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
            System.out.println("Start enumerator");
            for (Path child: children.toList()){
                if (Files.isDirectory(child)) {
                    enumerate(child);
                }
                else {
                    queue.put(child);
                    System.out.println("found file: " + child);
                }
            }
            System.out.println("End enumerator");
        }
    }


    public static void sortAndWriteFile(Path file, Path destDirectory) throws IOException  {
        ArrayList<String> content = new ArrayList<>();
        try (var in = new Scanner(file, StandardCharsets.UTF_8)){
            while (in.hasNextLine()) {
                content.add(in.next());
            }
            System.out.println("Sort file: " + file.getFileName());
            content.sort(String.CASE_INSENSITIVE_ORDER);
            System.out.println("File " + file.getFileName() + " was sorted");

            System.out.println("Write in file: " + destDirectory + "\\" + file.getFileName());
            Path destFile = Files.createFile(Paths.get(destDirectory + "\\" + file.getFileName()));
            FileWriter writer = new FileWriter(String.valueOf(destFile), false);
            for(String c: content) {
                writer.write(c + '\n');
            }
            writer.flush();
            System.out.println("Write in file: " + destDirectory + "\\" + file.getFileName() + " completed");
        }
    }

}
