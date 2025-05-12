package concurrency.unisexbathroom;

import concurrency.semaphore.iterationOne.Semaphore;

import java.util.ArrayList;
import java.util.List;

public class UnisexBathroom {

    Semaphore bathroom = new Semaphore(3);
    int maleCount = 0;
    int femaleCount = 0;


    public void useMaleBathroom() throws InterruptedException {

        synchronized (this) {
            while (femaleCount > 0) {
                wait();
            }
            bathroom.acquire();
            maleCount++;
        }
        System.out.println(Thread.currentThread().getName() + " Male entered");

        Thread.sleep(500);
        System.out.println(Thread.currentThread().getName() + " Male left");
        bathroom.release();
        synchronized (this) {
            maleCount--;

            notifyAll();
        }

    }

    public  void useFemaleBathroom() throws InterruptedException {

        synchronized (this) {
            while (maleCount > 0) {
                wait();
            }

            bathroom.acquire();
            femaleCount++;
        }
        System.out.println(Thread.currentThread().getName()+" Female entered");

        Thread.sleep(1000);
        System.out.println(Thread.currentThread().getName()+" Female left");
        bathroom.release();
        synchronized (this) {
            femaleCount--;

            notifyAll();
        }
    }

    public void test(){
        List<Thread>  girls = new ArrayList<>();
        List<Thread> boys = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        useMaleBathroom();
                    } catch (InterruptedException e) {}
                }
            });

            thread1.setName("Man_"+i);

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        useFemaleBathroom();
                    } catch (InterruptedException e) {}
                }
            });

            thread2.setName("Woman_"+i);
            girls.add(thread2);
            boys.add(thread1);
        }



//        for(Thread t: girls) t.start();
        for(Thread t: boys) t.start();
    }
}
