package week2;

import java.util.ArrayList;
import java.util.List;

public class Hello {

    static List<Integer> res = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hello, world!");
        hello_for();
        Point2D p = new Point2D();
        p.x = 5;
        p.y = 2;
        System.out.println(p);
        for(int i=1; i<10; i++)
            System.out.println(Fib(i));
    }

    public static int Fib(int n) {
        if(n == 1 || n == 2) {
            return 1;
        } else {
            return Fib(n-1) + Fib(n-2);
        }
    }

    private static void hello_for() {
        for(int i=0; i < 5; i++) {
            System.out.println("Hello, world!");
        }
    }
}