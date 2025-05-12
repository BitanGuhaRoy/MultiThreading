package concurrency.foobarn;

public class FooBar {

   int flag;
   public FooBar(int flag) { this.flag = flag; }
    public synchronized void foo() throws InterruptedException {
       while(flag==1) wait();
        System.out.println("Foo");
        flag=1;
        notifyAll();
    }
    public synchronized void bar() throws InterruptedException {
       while(flag!=1) wait();
       System.out.println("Bar");
        flag=0;
        notifyAll();
    }

}
