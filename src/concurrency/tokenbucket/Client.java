package concurrency.tokenbucket;


public class Client {

    public static void main(String[] args) throws InterruptedException {
        TokenBucket.runTestMaxToken();
    }
}
