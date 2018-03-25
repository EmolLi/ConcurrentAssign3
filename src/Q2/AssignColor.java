package Q2;

import java.util.HashMap;
import java.util.HashSet;

import static Q2.Main.graph;
import static Q2.Main.n;
import static Q2.Main.t;

/**
 * Created by emol on 3/23/18.
 */
public class AssignColor implements Runnable{
    int id;

    public AssignColor(int i){
        this.id = i;
    }


    @Override
    public void run() {
        assignColorToNodeInItsPartition();
    }


    private void assignColorToNodeInItsPartition(){
        // for nodes in its partition
        for (int i =  n / t * id ; i < n / t * (id + 1); i++){
            // if the node is in conflict set
            if (graph.conflict[i].get()){
                assignSmallestColor(i);
            }
        }
    }

    private void assignSmallestColor(int node){
        colorLoop: for (int color = 0; color < n; color++){
            inner: for (int neigbor : graph.g[node]){
                if (graph.colors.get(neigbor) == color){
                    continue colorLoop;
                }
            }
            graph.colors.set(node, color);
            break colorLoop;
        }
    }
}
