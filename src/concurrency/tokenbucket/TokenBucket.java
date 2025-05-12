package concurrency.tokenbucket;

import java.util.ArrayList;
import java.util.List;

public class TokenBucket {

    private long lastRequestTimeStamp;
    private final int capacity;
    private long availableTokens = 0;

    TokenBucket(int capacity) {
        this.capacity = capacity;
        this.lastRequestTimeStamp = System.currentTimeMillis();
    }

    public synchronized void getToken() throws InterruptedException {
        availableTokens += (System.currentTimeMillis() - lastRequestTimeStamp) / 1000;
        if (availableTokens > capacity) {
            availableTokens = capacity;
        }
        if (availableTokens == 0) {
            Thread.sleep(1000);
        } else {
            availableTokens--;
        }
        lastRequestTimeStamp = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + " got a token at " + lastRequestTimeStamp);
    }

    public static void runTestMaxToken() throws InterruptedException{
       final TokenBucket tokenBucket = new TokenBucket(5);
        List<Thread> threads = new ArrayList<>();
        Thread.sleep(5000);
        for(int i=0;i<12;i++){
            Thread t = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {
                        tokenBucket.getToken();
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            });

            t.setName("Thread_ "+(i+1));
            threads.add(t);

        }
        for(Thread t:threads){
            t.start();
        }
        for(Thread t:threads){
            t.join();
        }
    }
}
