package com.epam.rd.java.basic.practice5;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Class created to show simultaneously written characters into the same file.
 */
public class Part5 {
    private static Thread[] threads = new Thread[10];
    private static String fileName = "part5.txt";

    public static void main(final String[] args) {
        writeSimultaneously();
        printContent();
    }

    private static void printContent() {
        try (Scanner scanner = new Scanner(new FileReader(fileName))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.print(e.getMessage());
        }
    }

    private static void writeSimultaneously() {
        Path path = Paths.get(fileName);
        try {
            Files.delete(path);
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
        try (RandomAccessFile file = new RandomAccessFile(fileName, "rw")) {
            for (int i = 0; i < threads.length; i++) {
                int number = i;
                threads[i] = new Thread(() -> {
                    try {
                        file.seek(21 * (long) number);
                        for (int j = 0; j < 20; j++) {
                            file.write('0' + number);
                            Thread.sleep(1);
                        }
                        file.writeBytes(System.lineSeparator());
                    } catch (IOException | InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
                threads[i].start();
                threads[i].join();
            }
        } catch (FileNotFoundException | InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
    }
}
