package main.views;

import data.Style;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class Welcome extends View {
    private JPanel contentPane;
    private JButton choose;

    public Welcome() {
        super(Welcome.class.toString());
        super.jPanel= contentPane;
    }

    @Override
    public void flush() {
        super.flush();
    }

    @Override
    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        Style.setStyle(jPanels,buttons,null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        contentPane=new JPanel(){
            private final Image image = new ImageIcon("resource/img/背景.png").getImage();
            protected void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
    }
}
