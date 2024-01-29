package cn.com.wysha.debate_tournament.main.views;

import javax.swing.*;

/**
 * @author wysha
 */
abstract public class View {
    public final String viewName;
    public JPanel centerPanel;
    public View(String viewName) {
        this.viewName = viewName;
    }
    public void flush(){
        setStyle();
    }
    abstract public void setStyle();
}
