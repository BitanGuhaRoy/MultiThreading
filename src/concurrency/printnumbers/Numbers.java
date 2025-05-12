package concurrency.printnumbers;

public class Numbers {
    boolean zero;
    int dig;

    Numbers(boolean zero) {
        this.zero = zero;
        this.dig = 1;
    }

    public synchronized void printZero() throws InterruptedException {
        while (zero == false) wait();
        System.out.println("0");
        zero = false;
        notifyAll();
    }

    public synchronized void printNumber() throws InterruptedException {
        while (zero == true) wait();
        System.out.println(dig);
        dig++;
        zero = true;
        notifyAll();
    }


}
