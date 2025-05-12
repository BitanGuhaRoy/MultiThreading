package concurrency.unisexbathroom.iterationtwo;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Unisex {

    int male = 0;
    int female = 0;

    Semaphore semaphore = new Semaphore(3);

    public void enterMale() throws InterruptedException {

        synchronized (this) {
            while (female > 0)
                wait();
            semaphore.acquire();
            male++;
            System.out.println(Thread.currentThread().getName() + " entered male " + " total male " + male);
        }
        Thread.sleep(2000);
        // Use Toilet
        synchronized (this) {
            male--;
            semaphore.release();
            notifyAll();
            System.out.println(Thread.currentThread().getName() + " exited male " + " total male " + male);
        }
    }

    public void enterFemale() throws InterruptedException {
        synchronized (this) {
            while (male > 0) {
                wait();
            }
            semaphore.acquire();
            female++;
            System.out.println(Thread.currentThread().getName() + " entered female " + " total female " + female);
        }

        synchronized (this) {
            female--;
            System.out.println(Thread.currentThread().getName() + " exited female " + " total female " + female);
            semaphore.release();
            notifyAll();
        }
    }

    public void test() {
        List<Thread> girls = new ArrayList<>();
        List<Thread> boys = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        enterMale();
                    } catch (InterruptedException e) {
                    }
                }
            });

            thread1.setName("Man_" + i);

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        enterFemale();
                    } catch (InterruptedException e) {
                    }
                }
            });

            thread2.setName("Woman_" + i);
            girls.add(thread2);
            boys.add(thread1);
        }


        for (Thread t : girls) t.start();
        for (Thread t : boys) t.start();
    }
}
