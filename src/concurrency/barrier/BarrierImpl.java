package concurrency.barrier;

public class BarrierImpl {

    int count = 0;
    int maxCount = 0;
    int released =0;

    public synchronized void await() throws InterruptedException {
      while(count==maxCount) wait();

      count++;

      if(count==maxCount) {
          notifyAll();
          released = maxCount;
      }
      else {
          while(count<maxCount) wait();
      }

      released--;

      if(released==0){
          count=0;
          notifyAll();
      }
    }
}
