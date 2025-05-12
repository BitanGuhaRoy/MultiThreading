package concurrency.semaphore.iterationOne;

public class Client {
    public static void main(String[] args) throws InterruptedException {

        Semaphore semaphore=new Semaphore(1);
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        semaphore.acquire();
                        System.out.println("Ping " + i);
                    }
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < 5; i++) {
                        semaphore.release();
                        System.out.println("Pong " + i);
                    }
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
        t1.start();
        t.join();
        t1.join();


    }
}
