package main.views;

import data.Style;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class DebatePage extends View{
    private JPanel pane;
    private JLabel left;
    private JLabel right;

    public DebatePage() {
        super(DebatePage.class.toString());
        super.jPanel=pane;
        flush();
    }

    @Override
    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        Style.setStyle(jPanels,buttons,null);
        left.setForeground(Color.blue);
        right.setForeground(Color.red);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        pane=new JPanel(){
            private final Image image = new ImageIcon("").getImage();
            protected void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
    }
}
