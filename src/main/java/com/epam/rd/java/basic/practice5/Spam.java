package com.epam.rd.java.basic.practice5;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Spam class accepts an array of messages and a сoherent array of time intervals in milliseconds.
 */
public class Spam {

    private Thread[] threads;

    public Spam(final String[] messages, final int[] delays) {
        threads = new Thread[messages.length];
        for (int i = 0; i < messages.length; i++) {
            threads[i] = new Worker(messages[i], delays[i]);
        }
    }

    public static void main(final String[] args) {
        String[] messages = new String[]{"@@@", "bbbbbbb"};
        int[] times = new int[]{333, 222};
        Spam spam = new Spam(messages, times);
        spam.start();
        try {
            InputStreamReader scaner = new InputStreamReader(System.in);
            int code;
            byte[] lineSeparator = System.lineSeparator().getBytes();
            while ((code = scaner.read()) != -1) {
                if (lineSeparator.length > 1 && (code == lineSeparator[1] || code == lineSeparator[0])) {
                    break;
                }
            }
        } catch (IOException e) {
            Thread.currentThread().interrupt();
        }
        spam.stop();
    }

    public void start() {
        for (Thread t : threads) {
            t.start();
        }
    }

    public void stop() {
        for (Thread t : threads) {
            try {
                t.interrupt();
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static class Worker extends Thread {
        private String message;
        private int delay;

        Worker(String message, int delay) {
            this.message = message;
            this.delay = delay;
        }

        @Override
        public void run() {
            while (!isInterrupted()) {
                System.out.println(message);
                try {
                    sleep(delay);
                } catch (InterruptedException e) {
                    interrupt();
                }
            }
        }
    }
}
