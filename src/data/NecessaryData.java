
package data;

import java.io.ObjectInputStream;
import java.nio.file.Files;

/**
 * @author wysha
 */
public class NecessaryData extends AbstractWrittenData {
    public static NecessaryData necessaryData=new NecessaryData();

    public NecessaryData() {super("NecessaryData");necessaryData=this;}
    public final Setting setting = new Setting();

    @Override
    public void read() throws Throwable{
        if (file.exists()){
            necessaryData= (NecessaryData) new ObjectInputStream(Files.newInputStream(file.toPath())).readObject();
        }
    }
    public void createUIComponents(){

    }
}
