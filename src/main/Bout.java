package main;

public class Bout {
    public final String name;
    public final int startM;
    public final int startS;
    public final int waitTime;
    public Bout(String name, int startM, int startS, int waitTime) {
        this.name = name;
        this.startM = startM;
        this.startS = startS;
        this.waitTime = waitTime;
    }
}
