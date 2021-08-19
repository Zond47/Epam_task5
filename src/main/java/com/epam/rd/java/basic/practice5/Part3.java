package com.epam.rd.java.basic.practice5;

import static java.lang.Thread.sleep;

/**
 * Class created to compare the program functionality provided that the code is synchronized and not synchronized.
 */
public class Part3 {
    private int counter;
    private int counter2;
    private int numberOfThreads;
    private int numberOfIterations;
    private Thread[] threads;

    public Part3(int numberOfThreads, int numberOfIterations) {
        this.numberOfThreads = numberOfThreads;
        this.numberOfIterations = numberOfIterations;
        threads = new Thread[numberOfThreads];
    }

    public static void main(final String[] args) {
        Part3 part3 = new Part3(3, 5);
        part3.compare();
        part3.reset();
        part3.compareSync();
    }

    public void compare() {
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread() {
                @Override
                public void run() {
                    incrementsCounters();
                }
            };
            threads[i].start();
        }
        stop();
    }

    public void compareSync() {
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Thread(() -> {
                synchronized (this) {
                    incrementsCounters();
                }
            });
            threads[i].start();
        }
        stop();
    }

    private void stop() {
        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void reset() {
        counter = 0;
        counter2 = 0;
    }

    private void incrementsCounters() {
        for (int j = 0; j < numberOfIterations; j++) {
            System.out.print(counter + "==" + counter2 + "\n");
            counter++;
            try {
                sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            counter2++;
        }
    }
}
