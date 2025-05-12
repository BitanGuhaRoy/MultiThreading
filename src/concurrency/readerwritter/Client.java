package concurrency.readerwritter;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Client {

    public static void main(String[] args) {
        ReaderWritter readerWritter = new ReaderWritter();


        List<Thread> writterThreads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        readerWritter.acquireWriteLock();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        try {
                            readerWritter.releaseWriteLock();
                        } catch (InterruptedException e) {
                        }
                    }
                }
            });
            thread1.setName("Thread_ " + i);
            writterThreads.add(thread1);
        }


        List<Thread> readerThreads = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        readerWritter.acquireReadLock();
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    finally {
                        try {

                            readerWritter.releaseReadLock();
                        } catch (InterruptedException e) {
                        }
                    }
                }
            });
            thread1.setName("Thread_ " + i);
            readerThreads.add(thread1);
        }



        for (Thread thread : readerThreads) {
            thread.start();
        }
        for (Thread thread : writterThreads) {
            thread.start();
        }


    }
}
