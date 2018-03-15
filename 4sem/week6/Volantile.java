package week6;

public class Volantile {

    static volatile boolean done = true;
    final static Object obj1 = new Object();

    static class HelloWorldWriter implements Runnable {

        public void run() {
            while (true) {
                synchronized (obj1) {
                    System.out.println("Hello from thread 1! I'm changing `done` variable");
                    done = !done;
                    try {
                        obj1.wait(500);
                        Thread.currentThread().sleep(500);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    static class isDoneChecker implements Runnable {

        public void run() {
            while (true) {
                synchronized (obj1) {
                    System.out.println("Hello from thread 2! I'm checking `done` variable... " + done);
                    try {
                        obj1.wait(500);
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        HelloWorldWriter hwr = new HelloWorldWriter();
        isDoneChecker idc = new isDoneChecker();
        Thread t1 = new Thread(hwr);
        Thread t2 = new Thread(idc);
        t1.start();
        t2.start();
        System.out.println("0 thread is done");
    }
}
