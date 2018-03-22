package week7;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Executors {

    public static void main(String[] args) throws Exception {
        ExecutorService executor = java.util.concurrent.Executors.newSingleThreadExecutor();
        Callable<Integer> callable = () -> {
            System.out.println("Hello");
            return 0;
        };
        Future<Integer> future = executor.submit(callable);
        future.get();
        future.isDone();
        future.isCancelled();
        future.cancel(true);
    }
}
