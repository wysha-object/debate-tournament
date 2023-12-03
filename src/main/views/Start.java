package main.views;

import data.Config;
import data.Style;
import main.Bout;
import main.MainInterface;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class Start extends View{
    int current;
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
    private boolean b;
    private int lm;
    private int ls;
    private int rm;
    private int rs;
    public void run(){
        new Thread(() ->{
            for (current = 0; current < Config.config.bouts.size(); current++) {
                Bout bout = Config.config.bouts.get(current);
                stage.setText("第"+(current+1)+"辩:"+bout.name);
                lm = bout.startM;
                rm = bout.startM;
                ls = bout.startS;
                rs = bout.startS;
                stop.setEnabled(true);
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    if (b){
                        if (ls<=0){
                            lm--;
                            ls=60;
                        }
                        ls-=1;
                        if (stop.isEnabled()&&lm<=0&&ls<=0){
                            stop.setEnabled(false);
                            lm=0;
                            ls=0;
                            b=(rm<=0&&rs<=0);
                        }
                    }else {
                        if (rs<=0){
                            rm--;
                            rs=60;
                        }
                        rs-=1;
                        if (stop.isEnabled()&&rm<=0&&rs<=0){
                            stop.setEnabled(false);
                            rm=0;
                            rs=0;
                            b= true;
                        }
                    }
                    leftTime.setText(lm+":"+ ls);
                    rightTime.setText(rm+":"+ rs);
                    MainInterface.mainInterface.repaint();
                    if ((lm<=0&&ls<=0)&&(rm<=0&&rs<=0)){
                        break;
                    }
                }
                stage.setText("即将进入下一阶段");
                MainInterface.mainInterface.repaint();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
    public Start() {
        super(Start.class.toString());
        super.jPanel=pane;
        setStyle();
        stop.addActionListener(e -> b = !b);
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
