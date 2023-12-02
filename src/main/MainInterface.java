package main;

import data.NecessaryData;
import data.Style;
import main.views.View;
import main.views.Welcome;
import set.NecessarySet;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class MainInterface extends JFrame {
    final CardLayout cardLayout=new CardLayout();
    protected View current;
    protected void setCurrent(View abstractSubpages){
        cardLayout.show(show,abstractSubpages.viewName);
        current=abstractSubpages;
    }
    final public Welcome maker=new Welcome();
    public static MainInterface mainInterface;
    private JPanel contentPane;
    private JPanel show;
    private JLabel name;
    private JLabel mail;
    private JLabel v;
    private JLabel upLabel;
    private JButton set;
    private JButton exit;
    private JButton flush;

    public MainInterface() throws Throwable {
        setContentPane(contentPane);
        mainInterface=this;
        setUndecorated(true);
        setSize(
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
        );
        show.setLayout(cardLayout);
        show.add(maker.jPanel,maker.viewName);
        setCurrent(maker);
        flush();
        setVisible(true);
        for (float i=0;i<1;i+= 0.001F){
            Thread.sleep(1);
            setOpacity(i);
        }
        exit.addActionListener(e -> System.exit(0));
        set.addActionListener(e -> {
            NecessarySet necessarySet=new NecessarySet();
            necessarySet.setSize(
                    (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()*2/3,
                    (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()*2/3
            );
            necessarySet.setVisible(true);
            flush();
        });
        flush.addActionListener(e -> flush());
    }
    public static void main(String[] args) throws Throwable {
        NecessaryData.necessaryData.read();
        new MainInterface();
    }

    public void flush(){
        setStyle();
        current.flush();
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(show);
        buttons.add(v);
        buttons.add(upLabel);
        buttons.add(name);
        buttons.add(mail);
        buttons.add(exit);
        buttons.add(set);
        buttons.add(flush);
        Style.setStyle(jPanels,buttons,null);
    }
}
