package concurrency.barrier;

public class Barrier {
    int released=0;
    int count=0;
    int totalThreads;

    public Barrier(int totalThreads) {
        this.totalThreads=totalThreads;
    }

    public synchronized void await() throws InterruptedException {

        while(count==totalThreads) wait();

        System.out.println("<----Entering barrier--->");

        count++;

        if(count==totalThreads) {
            notifyAll();
            released = totalThreads;
        } else {
             while(count< totalThreads) wait();
        }
        released--;

        if(released==0){
            count=0;
            notifyAll();
        }
    }
}
