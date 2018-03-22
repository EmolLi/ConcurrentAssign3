package Q1;

import java.util.concurrent.ThreadLocalRandom;

import static Q1.Main.dequeuedItems;
import static Q1.Main.n;
import static Q1.Main.queue;

/**
 * Created by emol on 3/22/18.
 */
public class Dequeuer implements Runnable{
    int id;
    int itemCnt;    // number of items already dequeued

    public Dequeuer(int id){
        this.id = id;
        this.itemCnt = 0;
    }

    @Override
    public void run() {
        while (itemCnt < n){
            try{
                Node n = queue.deq(id);
                dequeuedItems[id][itemCnt] = n;
                itemCnt++;
                Thread.sleep(ThreadLocalRandom.current().nextInt(7, 13));
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
