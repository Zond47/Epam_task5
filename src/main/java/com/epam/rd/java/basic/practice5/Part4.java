package com.epam.rd.java.basic.practice5;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Scanner;

/**
 * Class was created to show parallelized search and usual one.
 */
public class Part4 {

    public static final String FILE_NAME = "part4.txt";
    static String[] numbers = getInput(FILE_NAME).split("[\n]+");

    public static void main(final String[] args) {
        long startTime = Instant.now().toEpochMilli();
        int max = parallelizedSearch();
        long endTime = Instant.now().toEpochMilli();
        System.out.println(max);
        System.out.println(endTime - startTime);

        startTime = Instant.now().toEpochMilli();
        max = search();
        endTime = Instant.now().toEpochMilli();
        System.out.println(max);
        System.out.println(endTime - startTime);
    }

    private static int parallelizedSearch() {
        Thread[] threads = new Thread[numbers.length];
        final int[] maximum = {0};
        for (int i = 0; i < threads.length; i++) {
            int finalI = i;
            threads[i] = new Thread(() -> {
                int tmpMax = run(maximum[0], numbers[finalI]);
                if (maximum[0] < tmpMax) {
                    maximum[0] = tmpMax;
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return maximum[0];
    }

    private static int run(int max, String row) {
        int maximum = max;
        String[] numbers = row.split(" ");
        for (String item : numbers) {
            try {
                int number = Integer.parseInt(item.trim());
                if (number > maximum) {
                    maximum = number;
                }
                Thread.sleep(1);
            } catch (NumberFormatException | InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return maximum;
    }


    private static int search() {
        final int[] maximum = {0};
        Thread t = new Thread(() -> {
            for (String item : numbers) {
                maximum[0] = run(maximum[0], item);
            }
        });
        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return maximum[0];
    }

    public static String getInput(String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new File(fileName), "UTF-8");
            while (scanner.hasNextLine()) {
                sb.append(scanner.nextLine()).append(System.lineSeparator());
            }
            scanner.close();
            return sb.toString().trim();
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
        return sb.toString();
    }
}
