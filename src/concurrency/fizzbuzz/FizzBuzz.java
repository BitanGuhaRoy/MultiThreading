package concurrency.fizzbuzz;

public class FizzBuzz {
    int dig =1;
    public synchronized void printNumber() throws InterruptedException {
        while(dig%3==0 || dig%5==0|| dig%15==0) wait();
        System.out.println(dig++);
        notifyAll();
    }
    public synchronized void fizz() throws InterruptedException {
        while(dig%3!=0 || dig%15==0) wait();
        System.out.println("Fizz");
        dig++;
        notifyAll();
    }
    public synchronized void buzz() throws InterruptedException {
        while(dig%5!=0 || dig%15==0) wait();
        System.out.println("Buzz");
        dig++;
        notifyAll();
    }
    public synchronized void fizzbuzz() throws InterruptedException {
        while(dig%15!=0) wait();
        System.out.println("FizzBuzz");
        dig++;
        notifyAll();
    }
}
