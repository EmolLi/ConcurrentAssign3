package Q1;


public abstract class UnboundedQueue {

    public abstract void enq(int i, int tID);
    public abstract Node deq(int tID) throws InterruptedException;
}
