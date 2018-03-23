package Q1;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by emol on 3/22/18.
 */
public class LockFreeQueue extends UnboundedQueue {
    AtomicReference<LNode> head, tail;  // FIXME: volatile here?

    public LockFreeQueue() {
        LNode headNode = new LNode(-1);
        head = new AtomicReference<>(headNode);   // sentinel node
        tail = new AtomicReference<>(headNode);
    }

    public void enq(int i, int tID) {

        LNode node = new LNode(i);
        while (true) {
            LNode last = tail.get();
            LNode next = last.next.get();
            if (last == tail.get()) {
                if (next == null) {
                    if (last.next.compareAndSet(next, node)) {
                        node.enqTime = System.nanoTime();
                        node.enqThreadId = tID;
//                        System.out.println("enq " + i + ", " + tID);
                        tail.compareAndSet(last, node);
                        return;
                    }
                } else {
                    tail.compareAndSet(last, next);
                }
            }
        }
    }

    public Node deq(int tID) throws InterruptedException {
        while (true) {
            LNode first = head.get();
            LNode last = tail.get();
            LNode next = first.next.get();
            if (first == head.get()) {
                if (first == last) {
                    if (next == null) {
                        Thread.sleep(1);
                    }
                    else {
                        tail.compareAndSet(last, next);
                    }
                } else {
                    if (head.compareAndSet(first, next)) {
                        next.deqTime = System.nanoTime();
                        next.deqThreadId = tID;
//                        System.out.println("deq " + next.id + ", " + tID);
                        return next;
                    }
                }
            }
        }
    }
}


class LNode extends Node{
    public AtomicReference<LNode> next;
    public LNode(int id) {
        this.id = id;
        next = new AtomicReference<>(null);
    }
}
