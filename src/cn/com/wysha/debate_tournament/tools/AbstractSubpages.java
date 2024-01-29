
package cn.com.wysha.debate_tournament.tools;

import javax.swing.*;

/**
 * @author wysha
 */
public abstract class AbstractSubpages {
    public final JDialog jDialog;
    public final String name;

    protected AbstractSubpages(String name, JDialog jDialog) {
        this.name = name;
        this.jDialog = jDialog;
    }

    /**
     * 确认时执行的操作
     */
    public abstract void onOkay();

    /**
     * 取消时执行的操作
     */
    public abstract void onCancel();
}
