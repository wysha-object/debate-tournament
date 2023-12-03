package data;

import java.io.Serializable;

/**
 * @author wysha
 */
public class Config implements Serializable {
    public static Config config;
    public final String name;
    public final String prosName;
    public final String consName;
    public final String thesis;

    public Config(String name, String prosName, String consName, String thesis) {
        this.name = name;
        this.prosName = prosName;
        this.consName = consName;
        this.thesis = thesis;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(name);
        return stringBuilder.toString();
    }
}
