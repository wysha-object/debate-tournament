package main;

import java.io.Serializable;
import java.util.List;

/**
 * @author wysha
 */
public record Bout(String name, int startM, int startS, int waitTime, java.util.List<Bout> bouts) implements Serializable {
    @Override
    public String name() {
        return name;
    }
    @Override
    public int startM() {
        return startM;
    }
    @Override
    public int startS() {
        return startS;
    }
    @Override
    public int waitTime() {
        return waitTime;
    }
    public String toString(List<Bout> bouts){
        int i;
        for (i=0;i<bouts.size();i++){
            if (bouts.get(i) ==this){
                break;
            }
        }
        return "第"+(i+1)+"辩" + (!name.isEmpty() ?":"+name:"");
    }
    @Override
    public String toString() {
        return toString(bouts);
    }
}
