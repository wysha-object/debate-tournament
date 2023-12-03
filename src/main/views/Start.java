package main.views;

import data.Config;
import data.Style;
import main.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class Start extends View{
    private JPanel pane;
    private JLabel left;
    private JLabel right;
    private JPanel vs;
    private JButton stop;
    private JPanel l;
    private JPanel r;
    private JLabel thesis;
    private JLabel stage;
    private JLabel leftTime;
    private JLabel rightTime;
    private int lm=5;
    private float ls=0;
    private int rm=5;
    private float rs=0;

    public Start() {
        super(Start.class.toString());
        super.jPanel=pane;
        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (ls==0){
                    lm--;
                    ls=60;
                }
                if (rs==0){
                    rm--;
                    rs=60;
                }
                ls-=0.01f;
                rs-=0.01f;
                leftTime.setText(lm+":"+ String.valueOf(ls).substring(0,5));
                rightTime.setText(rm+":"+String.valueOf(rs).substring(0,5));
                MainInterface.mainInterface.repaint();
            }
        }).start();
        setStyle();
    }
    @Override
    public void flush(){
        super.flush();
        left.setText("正方:"+ Config.config.prosName);
        right.setText("反方:"+ Config.config.consName);
        thesis.setText(Config.config.thesis);
    }

    @Override
    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(pane);
        jPanels.add(vs);
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
        vs=new JPanel(){
            private final Image image = new ImageIcon("resource/img/VS.png").getImage();
            protected void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
    }
}
