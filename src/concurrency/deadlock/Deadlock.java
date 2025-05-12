package concurrency.deadlock;

public class Deadlock {

    private int counter = 0;
    Object lock1 = new Object();
    Object lock2 = new Object();

    Runnable incrementer = new Runnable() {

        @Override
        public void run() {
            try{
                for(int i=0;i<100;i++){
                    increment();
                }
            }
            catch (Exception e){
            }
        }
    };


    Runnable decrementer = new Runnable() {
        @Override
        public void run() {
            try{
                for(int i=0;i<100;i++){
                    decrement();
                }
            }
            catch (Exception e){

            }
        }
    };


    public void increment(){
        synchronized(lock1){
            System.out.println("Acquired lock1");
            synchronized(lock2){
                counter++;
            }
        }
    }

    public void decrement(){
        synchronized(lock2){
            System.out.println("Acquired lock2");
            synchronized(lock1){
                counter--;
            }
        }
    }

    public void runTest(){
        Thread t1 = new Thread(incrementer);
        Thread t2 = new Thread(decrementer);
        t1.start();
        t2.start();
    }


}
