package Q1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by emol on 3/21/18.
 */
public class BlockingQueue extends UnboundedQueue{
    ReentrantLock enqLock, deqLock;
    Condition notEmptyCondition;
    volatile BNode head, tail;      // FIXME: is this volatile necessary?

    public BlockingQueue(){
        head = new BNode(-1);   // sentinel node
        tail = head;
        enqLock = new ReentrantLock();
        deqLock = new ReentrantLock();
        notEmptyCondition = deqLock.newCondition();
    }

    // FIXME: no synchronized keyword
    public void enq(int i, int tID){
        boolean mustWakeDequeuers = false;
        enqLock.lock();
        try {
            // there is no element in the queue, there may be dequeue threads waiting
            if (tail == head) mustWakeDequeuers = true;

            // add new node to queue
            BNode e = new BNode(i);
            e.enqThreadId = tID;
            tail.next = e;
            e.enqTime = System.currentTimeMillis();
//            System.out.println("enqueue: " + i);
            tail = e;
        } finally {
            enqLock.unlock();
        }

        if (mustWakeDequeuers){
            // the enqueuer acquires the enqLock before it signals the condition,
            // so the enqueuer cannot signal between the dequeuer’s two steps.
            // thus avoid the lost wake up problem
            deqLock.lock();
            try{
                notEmptyCondition.signalAll();
            } finally {
                deqLock.unlock();
            }
        }
    }




    public Node deq(int tID) throws InterruptedException{
        BNode result;
        deqLock.lock();
        try {
            while (head.next == null){
                notEmptyCondition.await();

            }

            result = head.next;
            head = head.next;
            result.deqThreadId = tID;
//            System.out.println("dequeue " + result.id);
            result.deqTime = System.currentTimeMillis();
        } finally {
            deqLock.unlock();
        }
        return result;
    }
}


class BNode extends Node{
    public BNode next;

    public BNode(int id){
        this.id = id;
    }
}