
package data;

import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * @author wysha
 */
public class DebateNecessaryData extends AbstractWrittenData {
    public static DebateNecessaryData deBateNecessaryData =new DebateNecessaryData();
    public DebateNecessaryData() {super("DebateNecessaryData");
        deBateNecessaryData =this;}
    public final Setting setting = new Setting();
    public final ArrayList<Config> configs=new ArrayList<>();
    @Override
    public void read() throws Throwable{
        if (file.exists()){
            deBateNecessaryData = (DebateNecessaryData) new ObjectInputStream(Files.newInputStream(file.toPath())).readObject();
        }
    }
}
