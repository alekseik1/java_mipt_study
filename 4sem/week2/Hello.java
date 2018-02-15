package week2;

public class Hello {

    public static void main(String[] args) {
        System.out.println("Hello, world!");
        hello_for();
    }

    private static void hello_for() {
        for(int i=0; i < 5; i++) {
            System.out.println("Hello, world!");
        }
    }
}