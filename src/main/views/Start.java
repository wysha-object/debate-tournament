package main.views;

import data.Config;
import data.Style;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class Start extends View{
    private JPanel pane;
    private JLabel left;
    private JPanel show;
    private JLabel right;
    private JPanel vs;
    private JButton stop;
    private JPanel l;
    private JPanel r;

    public Start() {
        super(Start.class.toString());
        super.jPanel=pane;
        setStyle();
    }
    @Override
    public void flush(){
        super.flush();
        left.setText("正方:"+ Config.config.prosName);
        right.setText("反方:"+ Config.config.consName);
    }

    @Override
    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(pane);
        jPanels.add(show);
        jPanels.add(vs);
        buttons.add(left);
        buttons.add(right);
        buttons.add(stop);
        Style.setStyle(jPanels,buttons,null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        pane=new JPanel(){
            private final Image image = new ImageIcon("").getImage();
            protected void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        show=new JPanel(){
            private final Image image = new ImageIcon("").getImage();
            protected void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        l=new JPanel(){
            private final Image image = new ImageIcon("resource/img/正方.png").getImage();
            protected void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        r=new JPanel(){
            private final Image image = new ImageIcon("resource/img/反方.png").getImage();
            protected void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
    }
}
