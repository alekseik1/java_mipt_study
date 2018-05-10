package reflection_api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class Example2 {

    static class Victim {
        private int x;

        public Victim(String s) {
            System.out.println(s);
        }

        int getX() {
            return x;
        }

        private Victim(int x) {
            System.out.println("Private" + x);
        }
    }

    public static void main(String[] args) {
        Object obj = new Victim("123");
        Class clazz = obj.getClass();
        Method methods[] = clazz.getMethods();
        Field fields[] = clazz.getFields();
        Constructor constructors[] = clazz.getDeclaredConstructors();
        System.out.println("------------METHODS------------");
        for(Method f: methods) {
            System.out.println(f);
        }
        System.out.println("------------FIELDS------------");
        for(Field f: fields) {
            System.out.println(f);
        }
        System.out.println("------------CONSTRUCTORS------------");
        for(Constructor f: constructors) {
            System.out.println(f);
        }
    }
}
