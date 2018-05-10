package reflection_api;

import java.util.HashMap;
import java.util.Map;

interface Processor {
    String action();
    void process();
}

public class BasicExample {

    Map<String, Processor> pMap = new HashMap<>();

    void doSmth(String action) {
        Processor p = pMap.get(action);
        if(p != null) {p.process();}
    }

    void add(Processor p){
        pMap.put(p.action(), p);
    }

    public static void main(String[] args) {
        BasicExample base = new BasicExample();
        base.add(new Processor() {
            @Override
            public String action() {
                return "Hello";
            }

            @Override
            public void process() {
                System.out.println("Hello");
            }
        });

        base.add(new Processor() {
            @Override
            public String action() {
                return "Test";
            }

            @Override
            public void process() {
                System.out.println("Testing method");
            }
        });

        base.doSmth("Hello");
        base.doSmth("Test");
    }
}
