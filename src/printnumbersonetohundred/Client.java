package printnumbersonetohundred;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    public static void main(String[] args) {

        // print 1 to 100 each from diff threads
        System.out.println("Hello, World!");

//        for(int i = 1; i <= 10; i++) {
//            NumberPrinter numberPrinter = new NumberPrinter(i);
//            Thread thread = new Thread(numberPrinter);
//            thread.start();
//        }

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        for(int i = 0; i < 10; i++) {
            NumberPrinter numberPrinter = new NumberPrinter(i);
            executorService.submit(numberPrinter);
        }
        executorService.shutdown();
    }
}
