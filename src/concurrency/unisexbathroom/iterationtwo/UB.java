package concurrency.unisexbathroom.iterationtwo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class UB {
    static String WOMEN = "women";
    static String MEN = "men";
    static String NONE = "none";

    String inUseBy = NONE;
    int empsInBathroom = 0;
    Semaphore maxEmps = new Semaphore(3);

    void useBathroom(String name) throws InterruptedException {
        System.out.println("\n" + name + " using bathroom. Current employees in bathroom = " + empsInBathroom + " " + System.currentTimeMillis());
//        Thread.sleep(3000);
        System.out.println("\n" + name + " done using bathroom " + System.currentTimeMillis());
    }

    void maleUseBathroom(String name) throws InterruptedException {

        synchronized (this) {
            while (inUseBy.equals(WOMEN)) {
                this.wait();
            }
            maxEmps.acquire();
            empsInBathroom++;
            inUseBy = MEN;

        }

        useBathroom(name);
        maxEmps.release();

        synchronized (this) {
            empsInBathroom--;

            if (empsInBathroom == 0) inUseBy = NONE;
            this.notifyAll();
        }
    }

    void femaleUseBathroom(String name) throws InterruptedException {

        synchronized (this) {
            while (inUseBy.equals(MEN)) {
                this.wait();
            }
            maxEmps.acquire();
            empsInBathroom++;
            inUseBy = WOMEN;
        }

        useBathroom(name);
        maxEmps.release();

        synchronized (this) {
            empsInBathroom--;

            if (empsInBathroom == 0) inUseBy = NONE;
            this.notifyAll();
        }
    }

    public void test(){
        List<Thread> girls = new ArrayList<>();
        List<Thread> boys = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        maleUseBathroom("Bitan");
                    } catch (InterruptedException e) {}
                }
            });

            thread1.setName("Man_"+i);

            Thread thread2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        femaleUseBathroom("Sarmi");
                    } catch (InterruptedException e) {}
                }
            });

            thread2.setName("Woman_"+i);
            girls.add(thread2);
            boys.add(thread1);
        }



        for(Thread t: girls) t.start();
        for(Thread t: boys) t.start();
    }

    public static void main(String[] args) {
       UB ub = new UB();
       ub.test();
    }
}