package main;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wysha
 */
public record Bout(String name, int start, int finishedWaitTime, LinkedList<Bout> bouts) implements Serializable {
    /**
     *
     */

    public String toString(List<Bout> bouts) {
        int i;
        for (i = 0; i < bouts.size(); i++) {
            if (bouts.get(i) == this) {
                break;
            }
        }
        return "第" + (i + 1) + "辩" + (!name.isEmpty() ? ":" + name : "");
    }

    @Override
    public String toString() {
        return toString(bouts);
    }
}
