package Q2;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;

import static Q2.Main.e;
import static Q2.Main.n;
import static Q2.Main.t;

/**
 * Created by emol on 3/23/18.
 * FIXME: Should I use atomicIntegerArray for colors?
 * Is the my way to partition nodes correct?
 * Why should I add nodes to conflict set atomically? Why will there be conflicts
 */
public class Graph {
    AtomicIntegerArray colors;   // color of nodes, for node i, its color is colors[i]
    LinkedList<Integer> g[];    // graph, used adjacen
    boolean[] conflict;     // conflict set
    AtomicBoolean[] newConflict;    // new conflict set

    public Graph(){
        this.colors = new AtomicIntegerArray(n);    // all nodes have initial color 0
        this.g = new LinkedList[n];
        for (int i = 0; i < n; i++){
            this.g[i] = new LinkedList<>();
        }
        this.conflict = new boolean[n];
        this.newConflict = new AtomicBoolean[n];
        Arrays.fill(newConflict, new AtomicBoolean(false));
        Arrays.fill(conflict, true);    // at first all nodes are in conflict set
        System.out.println("generating graph");
        generateGraph();
    }

/*
    or each node you need at least one edge.

    Start with one node. In each iteration, create a new node and a new edge. The edge is to connect the new node with a random node from the previous node set.

    After all nodes are created, create random edges until S is fulfilled. Make sure not to create double edges (for this you can use an adjacency matrix).
*/

    private void generateGraph(){
        for (int i = 1; i < n ; i++){
//            Start with one node. In each iteration, create a new node and a new edge. The edge is to connect the new node with a random node from the previous node set.
            int neighbor = (int) (Math.random() * i);   // previous node set : [0 ~ i-1]
            // add undirected edge
            addEdge(i, neighbor);
        }
        // random choose two nodes for the rest edges
        for (int i = 0; i < e - n + 1; i++){
            int v1 = -1;
            int v2 = -1;
            boolean validEdge = false;
            while (!validEdge){
                v1 = (int) (Math.random() * n);
                v2 = (int) (Math.random() * n);
                if (!g[v1].contains(v2)) validEdge = true;
            }
            addEdge(v1, v2);
        }

    }


    private void addEdge(int v1, int v2){
        System.out.println("ADD EDGE " + v1 + "-" + v2);
        this.g[v1].add(v2);
        this.g[v2].add(v1);
    }

    public boolean containsConflict(){
        for (boolean b: conflict){
            if (b) return true;
        }
        return false;
    }

    public void restartColoring(){
        for (int i = 0; i < n; i++){
            conflict[i] = newConflict[i].get();
            newConflict[i].set(false);
        }
    }

/*
    public void testGraph(){
        colorNode(0);
        for (int i = 0; i<n ; i++){
//            System.out.println(colors[i]);
            if (colors[i] == 0){
                System.err.println("eeee");
            }
        }

    }
    public void colorNode(int i){
        if (colors[i] == 1) return;
        colors[i] = 1;
        for (int k : g[i]){
            colorNode(k);
        }
    }
*/
}
