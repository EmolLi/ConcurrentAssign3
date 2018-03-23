package Q2;

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

            graph = new Graph();
//            graph.testGraph();




        } catch (Exception e) {
            e.printStackTrace();
            exit(1);
        }
    }
}
