package tests;

import junit.framework.TestCase;
import week6.CircularBuffer;

public class CircularBufferTest_ThreadSafe extends TestCase {

    private CircularBuffer<Integer> buffer;
    private int cap = 10;

    protected void setUp() {
        buffer = new CircularBuffer<>(cap);
    }

    public void testBothPopAndPush() {
        Thread t1 = new Thread(() -> {
            for(int i = 1; i < 5; i++) {
                buffer.push(5);
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread t2 = new Thread(() -> {
            for(int j = 1; j < 5; j++) {
                buffer.pop();
                try {
                    Thread.currentThread().sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        assertEquals(buffer.isEmpty(), true);
    }
}
