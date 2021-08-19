package com.epam.rd.java.basic.practice5;

import java.io.IOException;
import java.io.InputStream;

import static java.lang.Thread.currentThread;

/**
 * Spam simultaneously prints out the corresponding messages in the given time intervals.
 */
public class Part2 {
    private static final InputStream STD_IN = System.in;

    public static void main(final String[] args) {
        System.setIn(new MockInputStream());
        Thread t = new Thread(() -> Spam.main(null));
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            currentThread().interrupt();
        }
        System.setIn(STD_IN);
    }
}

class MockInputStream extends InputStream {
    @Override
    public int read() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            currentThread().interrupt();
        }
        return -1;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return super.read(b, 0, 8192);
    }
}
