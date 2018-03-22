package Q1;

import static java.lang.System.exit;

/**
 * Created by emol on 3/21/18.
 */
public class Main {
    public static int p, q, n;
    public static Node[][] dequeuedItems; // where all dequeue threads store dequeued items
    public static UnboundedQueue queue;


    public static void main(String[] args) {
        try {
            if (args.length < 4)
                throw new Exception("Missing arguments, only " + args.length + " were specified!");
            p = Integer.parseInt(args[0]);
            q = Integer.parseInt(args[1]);
            n = Integer.parseInt(args[2]);

            // the forth input is the sub question number:
            // 1 - blocking queue
            // 2 - lock free queue
            int questionNumber = Integer.parseInt(args[3]);
            if (p <= 0 || q <= 0 || n <= 0 || questionNumber < 0 || questionNumber > 2){
                System.err.println("Invalid arguments. input should be (p, q, n, questionNumber)");
                System.err.println("questionNumber: 1 - blocking queue");
                System.err.println("questionNumber: 2 - lock free queue");
                exit(-1);
            }

            Thread[] enqueuers = new Thread[p];
            Thread[] dequeuers = new Thread[q];
            dequeuedItems = new Node[q][n];
            queue = questionNumber == 1? new BlockingQueue() : new LockFreeQueue();

            // init enqueuers
            for (int i = 0; i< p; i++){
                Thread e = new Thread(new Enqueuer(i));
                enqueuers[i] = e;
                e.start();
            }

            // init dequeuers
            for (int i = 0; i< q; i++){
                Thread d = new Thread(new Dequeuer(i));
                dequeuers[i] = d;
                d.start();
            }

            // after all dequeuer terminates, terminate all enqueuers
            for (Thread d : dequeuers){
                d.join();
            }
            for (Thread e : enqueuers){
                e.interrupt();
            }



            // TODO: process output
            System.out.println("done");

        }catch (Exception e){
            e.printStackTrace();
            exit(1);
        }
    }
}
