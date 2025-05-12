package concurrency.atomicvslock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Client {

    static int count = 0;
    static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        test(true);
        test(false);
    }

    private synchronized static void increment() {
        count++;
    }

    static void test(boolean isAtomic) throws InterruptedException {
        ExecutorService exutorService = Executors.newFixedThreadPool(3);
        long start = System.currentTimeMillis();

        try {
            for (int i = 0; i < 10; i++) {

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int j = 0; j < 1000000; j++) {
                            if (isAtomic) {
                                atomicInteger.incrementAndGet();
                            } else {
                                increment();
                            }
                        }
                    }
                });
                exutorService.submit(t);
            }
        } finally {
            exutorService.shutdown();
            exutorService.awaitTermination(1, TimeUnit.HOURS);
        }

        System.out.println("Time taken for " + (isAtomic ? "atomic" : "lock") + " based counter: " + (System.currentTimeMillis() - start) + " ms");
    }

}
