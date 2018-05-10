package reflection_api;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;

public class Task2 {

    static class GovnoClass {
        public void add(Object o) {
            System.out.println("Ты что-то сделал. Молодец");
        }
    }

    static class MyClass2 {

        private Object obj1;
        private Object obj2;

        MyClass2() {
            if (System.currentTimeMillis() % 2 == 0) {
                obj1 = new HashSet<Integer>();
            } else {
                obj2 = new GovnoClass();
            }
        }
    }

    public static void main(String[] args) {
        MyClass2 c = new MyClass2();
        Field fields[] = c.getClass().getDeclaredFields();
        for(Field f: fields) {
            f.setAccessible(true);
            try {
                if (f.get(c) != null) {
                    System.out.println("Field " + f.getName() + " is not null!\nLet's put some values there...");
                    int values[] = new int[]{1, 2, 3};
                    Method add_method = f.get(c).getClass().getMethod("add", Object.class);
                    callMethod(f.get(c), add_method, values);
                    System.out.println("Field " + f.getName() + " now has:");
                    if(f.get(c).getClass().equals(HashSet.class)) {
                        for (int v : (HashSet<Integer>) f.get(c)) {
                            System.out.print(v + " ");
                        }
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void callMethod(Object a, Method m, int[] values) {
        for(int v: values) {
            try {
                m.invoke(a, v);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
