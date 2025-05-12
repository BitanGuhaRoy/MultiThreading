package concurrency.semaphore.iterationTwo;

public class Semaphore {

   public  int capacity;
    public int count;


    public Semaphore(int capacity) {
        this.capacity = capacity;
        this.count = 0;
    }

    public synchronized void acquire() throws InterruptedException{
        while(count==capacity) wait();
        count++;
        notify();
    }

    public synchronized void release() throws InterruptedException{
        while(count==0) wait();

        count--;

        notify();
    }



}
