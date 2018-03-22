package Q1;

import static Q1.Main.p;
import static Q1.Main.queue;

/**
 * Created by emol on 3/22/18.
 */
public class Enqueuer implements Runnable {
    int id;
    int itemCnt;    // number of items already produced

    public Enqueuer(int id){
        this.id = id;
        this.itemCnt = 0;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()){
            int id = getNextItemId();
            queue.enq(id);
        }
    }


    private int getNextItemId(){
        int itemId = itemCnt * p + id;
        itemCnt++;
        return itemId;
    }
}
