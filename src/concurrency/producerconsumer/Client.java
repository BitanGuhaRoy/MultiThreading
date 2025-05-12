package concurrency.producerconsumer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args)  throws InterruptedException{

        Queue<Integer> queue = new Queue<>(5);
        Thread t1 =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                for(int i=0;i<50;i++){
                        queue.enqueue(Integer.valueOf(i));
                }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });




        Thread t2 =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=0;i<25;i++){
                        queue.dequeue();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });



        Thread t3 =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int i=0;i<25;i++){
                        queue.dequeue();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
     t1.start();
     t2.start();
     t3.start();
    }
}
