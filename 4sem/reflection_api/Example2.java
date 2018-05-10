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
        System.out.println("------------------------------------");
        try {
            Field xField = clazz.getDeclaredField("x");
            xField.setAccessible(true);
            xField.setInt(obj, 100);
            System.out.println(((Victim) obj).getX());
        } catch(NoSuchFieldException e) {
            e.printStackTrace();
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }

        try {
            Method getXMethod = clazz.getDeclaredMethod("getX");
            Object res = getXMethod.invoke(obj);
            System.out.println(res);
        } catch(Exception e) {
            e.printStackTrace();
        }

        try {
            Constructor constr = clazz.getConstructor(String.class);
            constr.setAccessible(true);
            Victim v = (Victim) constr.newInstance("99");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
