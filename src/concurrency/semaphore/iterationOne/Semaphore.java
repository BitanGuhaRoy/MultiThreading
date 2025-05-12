package concurrency.semaphore.iterationOne;

public class Semaphore {

    int max;
    int count;

    public Semaphore(int max) {
        this.max = max;
        this.count = 0;
    }

    public synchronized void  acquire() throws InterruptedException{

        while(count==max) {
            wait();
        }

        count++;
        notify();
    }

    public synchronized void release() throws InterruptedException{
        while(count==0) {
            wait();
        }

        count--;
        notify();
    }
}
