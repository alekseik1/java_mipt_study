package week6;

public class CircularBufferTester {

    public static void main(String[] args) {
        CircularBuffer<Integer> buffer = new CircularBuffer<>(3);
        for(int i = 1; i < 10; i++) {
            System.out.println(buffer.push(i));
        }
        for(int i = 1; i < 10; i++) {
            System.out.println(buffer.pop());
        }
    }
}
