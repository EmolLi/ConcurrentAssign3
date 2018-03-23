package Q1;

import java.util.Arrays;

import static java.lang.System.exit;

/**
 * Created by emol on 3/21/18.
 */
public class Main {
    public static int p, q, n;
    public static Node[][] dequeuedItems; // where all dequeue threads store dequeued items
    public static UnboundedQueue queue;

    // action type
    public static final int ENQ = 1;
    public static final int DEQ = 2;


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

            System.out.println("================================================");
            System.out.println("==================INIT THREADS==================");
            System.out.println("====================RUNNING=====================");
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

            for (Thread e : enqueuers){
                e.join();
            }

            System.out.println("=====================DONE=======================");
            System.out.println("================================================");
            System.out.println("======================LOG=======================");
            // TODO: process output
            processLog();

        }catch (Exception e){
            e.printStackTrace();
            exit(1);
        }
    }

    public static void processLog(){
        // q dequeue threads, each dequeue n items, each items have 2 actions (enq, deq)
        Action[] log = new Action[q*n*2];
        //test
        Action[] deqLog = new Action[q*n];
        Action[] enLog = new Action[q*n];
        int ai = 0;
        int bi = 0;
        for (int i = 0; i < q; i++){
            for (int j = 0; j < n; j++){
                enLog[bi] = new Action(dequeuedItems[i][j].enqTime, ENQ, dequeuedItems[i][j].id, dequeuedItems[i][j]
                        .enqThreadId);
                deqLog[bi++] = new Action(dequeuedItems[i][j].deqTime, DEQ, dequeuedItems[i][j].id, dequeuedItems[i][j]
                        .deqThreadId);
                log[ai++] = new Action(dequeuedItems[i][j].enqTime, ENQ, dequeuedItems[i][j].id, dequeuedItems[i][j].enqThreadId);
                log[ai++] = new Action(dequeuedItems[i][j].deqTime, DEQ, dequeuedItems[i][j].id, dequeuedItems[i][j].deqThreadId);
            }
        }
        Arrays.sort(log);
        Arrays.sort(enLog);
        Arrays.sort(deqLog);
        // print

        for (Action i : log){
            System.out.println(i);
        }/*
    for(Action i : enLog){
        System.out.println(i);
    }
    for(Action j : deqLog){
        System.out.println(j);
    }*/
        for (int i = 0; i< q*n; i++){
            if (enLog[i].id != deqLog[i].id ) {
                System.err.println(enLog[i]);
                System.err.println(deqLog[i]);
            }

        }
    }



}


