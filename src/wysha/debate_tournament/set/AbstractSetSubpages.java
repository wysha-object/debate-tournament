
package wysha.debate_tournament.set;

import wysha.debate_tournament.tools.AbstractSubpages;

import javax.swing.*;

/**
 * @author wysha
 */
public abstract class AbstractSetSubpages extends AbstractSubpages {
    protected AbstractSetSubpages(String name, JDialog jDialog) {
        super(name, jDialog);
    }
    @Override
    public void onOkay(){
        save();
        jDialog.dispose();
    }

    /**
     * 保存设置
     */
    public abstract void save();
}
