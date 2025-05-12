package concurrency.readerwritter.iterationTwo;

public class ReaderWritter {

    int reader=0;
    int writer=0;
    public synchronized void acquireReadLock() throws InterruptedException {
       while(writer>0){
           wait();
       }
       reader++;
    }
    public synchronized void releaseReadLock() throws InterruptedException {
        while(reader==0){
            wait();
        }
        reader--;
        notify();
    }
    public synchronized void acquireWriteLock() throws InterruptedException {
        while(reader>0 || writer>0){
            wait();
        }
        writer++;
    }
    public synchronized void releaseWriteLock() throws InterruptedException {
        while(writer==0){
            wait();
        }
        writer--;
        notify();
    }


}
