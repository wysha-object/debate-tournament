package main.views;

import data.Config;
import data.Style;
import main.Bout;
import main.MainInterface;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;

/**
 * @author wysha
 */
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
    private static class I{
        int i;
        @Override
        public String toString() {
            return String.valueOf(i);
        }
    }
    private final I lm= new I();
    private final I ls= new I();
    private final I rm= new I();
    private final I rs= new I();
    public void run(){
        new Thread(() ->{
            for (current = 0; current < Config.config.bouts().size(); current++) {
                b=true;
                Bout bout = Config.config.bouts().get(current);
                stage.setText("第"+(current+1)+"辩:"+bout.name());
                lm.i = bout.startM();
                rm.i = bout.startM();
                ls.i = bout.startS();
                rs.i = bout.startS();
                stop.setEnabled(true);
                do {
                    I m;
                    I s;
                    if (b){
                        m=lm;
                        s=ls;
                    }else {
                        m=rm;
                        s=rs;
                    }
                    if (s.i <= 0) {
                        m.i--;
                        s.i = 60;
                    }
                    if (m.i * 60 + s.i-1 == (bout.startM() * 60 + bout.startS()) / 2) {
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("resource/media/Ring02.wav")));
                            clip.start();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    s.i -= 1;
                    if (m.i <= 0 && s.i <= 0) {
                        if (stop.isEnabled()){
                            stop.setEnabled(false);
                            m.i = 0;
                            s.i = 0;
                            b = !b;
                        }
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(AudioSystem.getAudioInputStream(new File("resource/media/Alarm04.wav")));
                            clip.start();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    leftTime.setText(lm + ":" + ls);
                    rightTime.setText(rm + ":" + rs);
                    MainInterface.mainInterface.repaint();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } while ((lm.i > 0 || ls.i > 0) || (rm.i > 0 || rs.i > 0));
                if(current==Config.config.bouts().size()-1){
                    for (int i=bout.waitTime();i>=0;i--){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        stage.setText("本次辩论赛即将结束:"+i);
                        MainInterface.mainInterface.repaint();
                    }
                }else {
                    for (int i= bout.waitTime();i>=0;i--){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        stage.setText("即将进入下一阶段:"+i);
                        MainInterface.mainInterface.repaint();
                    }
                }
            }
            MainInterface.mainInterface.setCurrent(MainInterface.mainInterface.welcome);
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
        left.setText("正方:"+ Config.config.prosName());
        right.setText("反方:"+ Config.config.consName());
        thesis.setText(Config.config.thesis());
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
