package concurrency.foobarn;

public class Client {

    public static void main(String[] args) {


        FooBar fooBar = new FooBar(1);
        Thread tf = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<5;i++){
                    try {
                        fooBar.foo();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });


        Thread tb = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<5;i++){
                    try {
                        fooBar.bar();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        tf.start();
        tb.start();
    }
}
