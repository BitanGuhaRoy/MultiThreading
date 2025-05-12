package concurrency.printnumbers;

public class Client {
    public static void main(String[] args) {

        Numbers numbers= new Numbers(true);

        Thread th = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<10;i++){
                    try {
                        numbers.printZero();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        Thread th2 = new Thread(new Runnable() {
            public void run(){
                for(int i=0;i<10;i++){
                    try {
                        numbers.printNumber();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        th.start();
        th2.start();
    }
}
