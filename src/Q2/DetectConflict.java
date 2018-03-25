package Q2;

import static Q2.Main.graph;
import static Q2.Main.n;
import static Q2.Main.t;

/**
 * Created by emol on 3/23/18.
 */
public class DetectConflict implements Runnable {
    int id;

    public DetectConflict(int id){
        this.id = id;
    }

    public void run(){
        detectConflictInItsPartition();
    }

    private void detectConflictInItsPartition(){
        // for nodes in its partition
        for (int i =  n / t * id ; i < n / t * (id + 1); i++){
            for (int neighbor : graph.g[i]){
                if (graph.colors.get(i) == graph.colors.get(neighbor)){
                    // add i to conflict set
                    System.out.println(i + ", " + neighbor);
                    graph.conflict[i].set(true);
                }
            }
        }
    }
}
