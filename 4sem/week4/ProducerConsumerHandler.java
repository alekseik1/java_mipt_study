package week4;

public class ProducerConsumerHandler {

    public static void main(String[] args) throws InterruptedException {
        final ProducerConsumer pc = new ProducerConsumer();

        Thread thread1 = new Thread(() -> {
            try {
                pc.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                pc.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }
}
