package concurrency.semaphore.iterationTwo;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {

        Semaphore semaphoreA = new Semaphore(1);
        Semaphore semaphoreB = new Semaphore(1);

        semaphoreA.count =0;
        semaphoreB.count =1;

        List<Thread> pings = new ArrayList<>();
        // ping -> pong
        for(int i=0;i<5;i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphoreA.release();
                        System.out.println("ping " + Thread.currentThread().getName());
                        semaphoreB.acquire();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            pings.add(t);

        }

        List<Thread> pongs = new ArrayList<>();
        for(int i=0;i<5;i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        semaphoreB.release();
                        System.out.println("pong " + Thread.currentThread().getName());
                        semaphoreA.acquire();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }


                }
            });

            pongs.add(t);

        }

        for(Thread t:pings){
            t.start();
        }
        for(Thread t:pongs){
            t.start();
        }

    }
}
