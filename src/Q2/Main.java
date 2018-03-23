package Q2;

import Q1.Dequeuer;

import static java.lang.System.exit;

/**
 * Created by emol on 3/23/18.
 */
public class Main {
    public static int n, e, t;
    public static Graph graph;

    public static void main(String[] args) {
        try {
            if (args.length < 3)
                throw new Exception("Missing arguments, only " + args.length + " were specified!");

//            n > 3 (the number of nodes in the graph),
//            e > 0 (the number of undirected edges in the graph), and t > 0 (the number of threads to use). I
            n = Integer.parseInt(args[0]);
            e = Integer.parseInt(args[1]);
            t = Integer.parseInt(args[2]);
            if (n <= 3 || e < 0 || t <= 0) {
                throw new Exception("Invalid arguments.");
            }
            if (e < n -1){
                throw new Exception("Too few edges for the graph.");
            }

            System.out.println("=================INIT GRAPH================");
            graph = new Graph();
//            graph.testGraph();
            System.out.println("================COLOR GRAPH================");
            colorGraph();
            System.out.println("=====================DONE==================");




        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("asdfa");
            exit(1);
        }
    }


    public static void colorGraph() throws InterruptedException{
        Thread[] threads = new Thread[t];
        int itr = 1;
        while (graph.containsConflict()){
            System.err.println("itr: " + itr);
            for (int i = 0; i< t; i++){
                threads[i] = new Thread(new AssignColor(i));
                threads[i].start();
            }

            // join
            for (int i = 0; i< t; i++){
                threads[i].join();
            }

            for (int i = 0; i < t; i++){
                threads[i] = new Thread(new DetectConflict(i));
                threads[i].start();
            }
            for (int i = 0; i< t; i++){
                threads[i].join();
            }

            graph.restartColoring();
            itr++;
        }
    }
}
