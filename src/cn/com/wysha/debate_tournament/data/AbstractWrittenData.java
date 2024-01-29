
package cn.com.wysha.debate_tournament.data;

import cn.com.wysha.debate_tournament.tools.Prompt;

import java.io.File;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author wysha
 */
public abstract class AbstractWrittenData implements Serializable {
    public final File file;

    protected AbstractWrittenData(String name) {
        file = new File(name);
    }

    /**
     * 读取数据
     *
     * @throws Throwable 可能的异常
     */
    public abstract void read() throws Throwable;

    /**
     * 写入数据
     *
     * @throws Throwable 可能的异常
     */
    public void write() throws Throwable {
        new ObjectOutputStream(Files.newOutputStream(Paths.get(file.getAbsolutePath()))).writeObject(this);
        new Prompt("已保存至" + file.getAbsolutePath()).setVisible(true);
    }
}
