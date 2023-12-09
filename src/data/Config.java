package data;

import main.Bout;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @param prosName 正方名
 * @param consName 反方名
 * @author wysha
 */
public record Config(
        String name,
        String prosName,
        String consName,
        String thesis,
        LinkedList<Bout> bouts
) implements Serializable {
    @Override
    public String name() {
        return name;
    }

    @Override
    public String prosName() {
        return prosName;
    }

    @Override
    public String consName() {
        return consName;
    }

    @Override
    public String thesis() {
        return thesis;
    }

    @Override
    public LinkedList<Bout> bouts() {
        return bouts;
    }

    public static Config config;

    @Override
    public String toString() {
        return name+"           论题:"+thesis+"           包含回合数:"+bouts.size();
    }
}
