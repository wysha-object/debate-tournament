package main;

import java.io.Serializable;

/**
 * @author wysha
 */
public record Bout(String name, int startM, int startS, int waitTime) implements Serializable {
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

    @Override
    public String toString() {
        return name;
    }
}
