package tests;

import junit.framework.TestCase;
import week6.CircularBuffer;

public class CircularBufferTest extends TestCase {

    private CircularBuffer<Integer> buffer;
    private int cap = 5;

    protected void setUp() {
         buffer = new CircularBuffer<>(cap);
    }

    public void testPush() {
        for(int i = 0; i < 2*cap; i++) {
            if(i < cap) {
                assertEquals(buffer.push(i), true);
            } else {
                assertEquals(buffer.push(i), false);
            }
        }
    }
}
