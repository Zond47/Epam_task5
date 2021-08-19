package com.epam.rd.java.basic.practice5;

import static java.lang.Thread.*;

/**
 * Create a child thread that, during about 2 seconds, would print out its name every 1/2 seconds.
 */

public class Part1 {

    public static void main(String[] args) {
        Thread t = new Thread() {
            @Override
            public void run() {
                while (!interrupted()) {
                    System.out.print(currentThread().getName() + "\n");
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        interrupt();
                    }
                }
            }
        };

        Runnable r = () -> {
            while (!interrupted()) {
                System.out.print(currentThread().getName() + "\n");
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    currentThread().interrupt();
                }
            }
        };

        runThread(t);
        runThread(new Thread(r));
    }

    private static void runThread(Thread thread) {
        thread.start();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            currentThread().interrupt();
        }
        thread.interrupt();
        try {
            thread.join();
        } catch (InterruptedException e) {
            currentThread().interrupt();
        }
    }
}
