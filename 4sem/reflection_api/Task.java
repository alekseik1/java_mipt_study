package reflection_api;

import java.util.HashMap;
import java.util.Map;

// Задача -- переписать следующий класс с использованием Reflection API
public class Task {

    void doSmth(String action) {
        if(action.equals("hello")){ System.out.println("hello"); }
        else if(action.equals("bye")){ System.out.println("bye"); }
    }

    // А вот и решение
    interface Processor {
        void process();
    }

    public static void main(String[] args) {
        Map<String, Processor> m = new HashMap<>();
        m.put("hello", () -> System.out.println("hello"));
        m.put("bye", () -> System.out.println("bye"));
        m.get("hello").process();
        m.get("bye").process();
    }

}
