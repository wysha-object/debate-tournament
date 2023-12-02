package data;

import main.views.DebatePage;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author wysha
 */
public class Config implements Serializable {
    public static Config config;
    public final String name;
    public final String prosName;
    public final String consName;
    public final ArrayList<DebatePage> debatePages=new ArrayList<>();

    public Config(String name, String prosName, String consName) {
        this.name = name;
        this.prosName = prosName;
        this.consName = consName;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append(name);
        return stringBuilder.toString();
    }
}
