package data;

import main.Bout;

import java.io.Serializable;
import java.util.List;

/**
 * @author wysha
 */
public class Config implements Serializable {
    public static Config config;
    public final String name;
    public final String prosName;
    public final String consName;
    public final String thesis;
    public final List<Bout> bouts;

    public Config(String name, String prosName, String consName, String thesis, List<Bout> bouts) {
        this.name = name;
        this.prosName = prosName;
        this.consName = consName;
        this.thesis = thesis;
        this.bouts = bouts;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(name);
        return stringBuilder.toString();
    }
}
