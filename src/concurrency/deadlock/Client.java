package concurrency.deadlock;

public class Client {

    public static void main(String[] args) {
        Deadlock d = new Deadlock();
        d.runTest();

    }
}
