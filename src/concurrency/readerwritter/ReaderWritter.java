package concurrency.readerwritter;

public class ReaderWritter {
   private boolean writeLocked = false;
    int reader=0;
    public synchronized void acquireReadLock() throws InterruptedException {
        while(writeLocked){
            wait();
        }
        reader++;
        System.out.println("Reader "+ Thread.currentThread().getName() + " acquired read lock "+ "total reader "+reader);
        notifyAll();
    }
    public synchronized void releaseReadLock() throws InterruptedException {
        while(reader==0){
            wait();
        }
        reader--;
        notifyAll();
    }
    public synchronized void acquireWriteLock() throws InterruptedException {
        while(reader>0 || writeLocked){
            wait();
        }
        writeLocked=true;
        System.out.println("Writter "+ Thread.currentThread().getName() + " acquired write lock "+ "total reader "+reader);
        Thread.sleep(2000);
        notifyAll();
    }
    public synchronized void releaseWriteLock() throws InterruptedException {
        while(!writeLocked){
            wait();
        }
        writeLocked=false;
        notifyAll();
    }
}
