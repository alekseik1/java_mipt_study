package week6;

public class CircularBufferTester {

    public static void main(String[] args) {
        /*
        CircularBuffer<Integer> buffer = new CircularBuffer<>(3);
        for(int i = 1; i < 10; i++) {
            System.out.println(buffer.push(i));
        }
        for(int i = 1; i < 10; i++) {
            System.out.println(buffer.pop());
        }
        */
        CircularBufferThread<Integer> buffer = new CircularBufferThread<>(3);
        Thread t1 = new Thread(() -> {
            for(int i = 0; i < 5; i++) {
                buffer.push(i*i);
            }
        });
        t1.start();
        try {
            Thread.currentThread().sleep(100);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2 = new Thread(() -> {
            for(int i = 0; i < 5; i++) {
                buffer.pop();
            }
        });
        t2.start();
        System.out.println("Has t2.isAlive: " + t2.isAlive());
        System.out.println("Buffer is empty: " + buffer.isEmpty());
    }
}
