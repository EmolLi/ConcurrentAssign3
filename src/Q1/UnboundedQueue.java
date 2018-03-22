package Q1;


public abstract class UnboundedQueue {

    public abstract void enq(int i);
    public abstract Node deq() throws InterruptedException;
}
