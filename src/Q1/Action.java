package Q1;

import static Q1.Main.ENQ;

class Action implements Comparable<Action>{
    long timeStamp;
    int type;
    int tID;
    int id;

    public Action(long time, int type, int id, int tID){
        this.timeStamp = time;
        this.type = type;
        this.id = id;
        this.tID = tID;
    }

    @Override
    public int compareTo(Action o) {
        return (this.timeStamp - o.timeStamp > 0 ? 1 : this.timeStamp - o.timeStamp == 0? 0 : -1);
    }

    @Override
    public String toString(){
        return "Time: " + this.timeStamp + ",  Op: " + (type == ENQ? "ENQ" : "DEQ") + ",  Node id: " + id + ",  " +
                "Thread:" +
                "  " + (type == ENQ? "ENQ-" : "DEQ-") + tID;
    }
}