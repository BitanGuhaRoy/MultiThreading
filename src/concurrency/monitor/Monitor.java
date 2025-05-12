package concurrency.monitor;

public class Monitor {
    private int balance;

    public void withdraw(int amount) throws InterruptedException {
        synchronized (this) {
            while (balance < amount) {
                this.wait();
                System.out.println(Thread.currentThread().getName() + " waiting ...");
            }

            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " withdrawing " + amount);
        }
    }

    public synchronized void deposit(int amount) {
        balance+= amount;

        System.out.println(Thread.currentThread().getName() + " depositing " + amount);

        notify();
    }


    public int getBalance() {
        return balance;
    }
}
