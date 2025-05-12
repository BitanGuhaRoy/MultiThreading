package concurrency.uberride;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class UberRide {


    int republicans=0;
    int democrats=0;

    Semaphore repWaiting = new Semaphore(0);
    Semaphore demWaiting = new Semaphore(0);
    CyclicBarrier barrier = new CyclicBarrier(4);

    ReentrantLock lock = new ReentrantLock();

    void seatRepublicans() throws InterruptedException, BrokenBarrierException {
            boolean leader = false;
            lock.lock();
            republicans++;
            if(republicans==4){
                repWaiting.release(3);
                leader = true;
                republicans-=4;
            }
            else if(republicans==2 && democrats>=2){
                repWaiting.release(1);
                demWaiting.release(2);
                leader = true;
                republicans-=2;
                democrats-=2;
            }
            else {
                lock.unlock();
                repWaiting.acquire();
            }

            seated();
            barrier.await();

            if(leader){
                drive();
                lock.unlock();
            }
    }
    void seatDemocrats() throws InterruptedException, BrokenBarrierException {
        boolean leader = false;
        lock.lock();

        democrats++;
        if(democrats==4){
            demWaiting.release(3);
            leader = true;
            democrats-=4;
        }
        else if(democrats==2 && republicans>=2){
            demWaiting.release(1);
            repWaiting.release(2);
            leader = true;
            democrats-=2;
            republicans-=2;
        }
        else {
            lock.unlock();
            demWaiting.acquire();
        }


        seated();
        barrier.await();

        if(leader){
            drive();
            lock.unlock();
        }
    }
    void seated() {
        System.out.println(Thread.currentThread().getName() + "  seated");
        System.out.flush();
    }
    void drive() {
        System.out.println("Uber Ride on Its wayyyy... with ride leader " + Thread.currentThread().getName());
        System.out.flush();
    }

    public static void runTest() throws InterruptedException {


        final UberRide uberSeatingProblem = new UberRide();
        Set<Thread> allThreads = new HashSet<Thread>();

        for (int i = 0; i < 10; i++) {

            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        uberSeatingProblem.seatDemocrats();
                    } catch (InterruptedException ie) {
                        System.out.println("We have a problem");

                    } catch (BrokenBarrierException bbe) {
                        System.out.println("We have a problem");
                    }

                }
            });
            thread.setName("Democrat_" + (i + 1));
            allThreads.add(thread);

            Thread.sleep(50);
        }

        for (int i = 0; i < 14; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    try {
                        uberSeatingProblem.seatDemocrats();
                    } catch (InterruptedException ie) {
                        System.out.println("We have a problem");

                    } catch (BrokenBarrierException bbe) {
                        System.out.println("We have a problem");
                    }
                }
            });
            thread.setName("Republican_" + (i + 1));
            allThreads.add(thread);
            Thread.sleep(20);
        }

        for (Thread t : allThreads) {
            t.start();
        }

        for (Thread t : allThreads) {
            t.join();
        }
    }
}