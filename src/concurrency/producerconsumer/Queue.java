package concurrency.producerconsumer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Queue <T>{
    LinkedList<T> items;
    int capacity;

    public Queue(int capacity) {
        this.capacity = capacity;
        items = new LinkedList<>();
    }

    public synchronized void enqueue(T item) throws InterruptedException {
        while(items.size() == capacity) {
            wait();
        }
        items.add(item);
        System.out.println("Enqueued " + item);
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {

        while(items.isEmpty()) {
            wait();
        }
        T item = items.removeFirst();
        notifyAll();
        System.out.println("Dequeued " + item);
        return item;



    }

}
