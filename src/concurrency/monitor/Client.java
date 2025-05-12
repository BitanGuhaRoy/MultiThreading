package concurrency.monitor;

public class Client {
    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        Runnable withdrawal = new Runnable(){
            @Override
            public void run() {
                try {
                    monitor.withdraw(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };


        Runnable deposit = new Runnable(){
            @Override
            public void run() {
                monitor.deposit(100);

            }
        };

        Thread t1 = new Thread(withdrawal);
        Thread t2 = new Thread(deposit);

        t1.start();
        t2.start();


    }



}
