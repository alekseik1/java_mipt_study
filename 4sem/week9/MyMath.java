package week9;

public class MyMath {

    public static int add(int a, int b) throws OverflowException {
        if(((a > Integer.MAX_VALUE - b) && (b>=0)) || ((a < Integer.MIN_VALUE - b) && (b<0)))
            throw new OverflowException("Results doesn't fit to `int`!");
        return a+b;
    }

    public static double div(int a, int b) throws DivByZeroException {
        if(b == 0)
            throw new DivByZeroException("Division by zero!");
        return a/b;
    }

    public static void main(String[] args) {
        int a = Integer.MIN_VALUE+3;
        int b = -2;
        try {
            System.out.println(MyMath.add(a, b) == a+b);
        } catch(OverflowException e) {
            e.printStackTrace();
        }

        a = 5;
        b = 2;
        try {
            System.out.println(MyMath.div(a, b) == 5/2);
        } catch(DivByZeroException e) {
            e.printStackTrace();
        }
    }
}
