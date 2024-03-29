package cn.com.wysha.debate_tournament.main.views;

import cn.com.wysha.debate_tournament.main.MainInterface;
import cn.com.wysha.debate_tournament.data.Config;
import cn.com.wysha.debate_tournament.data.Style;
import cn.com.wysha.debate_tournament.main.Bout;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;

import static java.lang.Thread.sleep;

/**
 * @author wysha
 */
public class Start extends View{
    int current;
    Thread thread;
    private JPanel pane;
    private JLabel left;
    private JLabel right;
    private JPanel vs;
    private JButton next;
    private JPanel l;
    private JPanel r;
    private JLabel thesis;
    private JLabel stage;
    private JLabel leftTime;
    private JLabel rightTime;
    private JPanel down;
    private JButton stop;
    private JButton finish;
    private JButton jump;
    private JButton go;
    private JPanel center;
    private boolean b;
    private Thread t;
    private static class I{
        boolean b;
        long i;
        @Override
        public String toString() {
            return String.valueOf(i);
        }
    }
    private final I ls= new I();
    private final I rs= new I();
    private I s;
    private long currentTimeMillis;
    public Start() {
        super(Start.class.toString());
        super.centerPanel =pane;
        setStyle();
        next.addActionListener(e -> b = !b);
        stop.addActionListener(e -> {
            thread.suspend();
            t.suspend();
            stop.setEnabled(false);
            go.setEnabled(true);
        });
        go.addActionListener(e -> {
            thread.resume();
            t.resume();
            currentTimeMillis = System.currentTimeMillis();
            stop.setEnabled(true);
            go.setEnabled(false);
        });
        finish.addActionListener(e -> {
            thread.stop();
            t.stop();
            MainInterface.mainInterface.setCurrent(MainInterface.mainInterface.welcome);
        });
        jump.addActionListener(e -> t.stop());
    }

    public void run(){
        thread= new Thread(() ->{
            go.setEnabled(false);
            for (current = 0; current < Config.config.bouts().size(); current++) {
                b=true;
                Bout bout = Config.config.bouts().get(current);
                stage.setText("第"+(current+1)+"辩" + (!bout.name().isEmpty() ?":"+bout.name():""));
                currentTimeMillis=System.currentTimeMillis();
                ls.i = bout.start()* 1000L;
                rs.i = bout.start()* 1000L;
                ls.b=true;
                rs.b=true;
                center.setVisible(true);
                flush();
                Runnable runnable1 = () -> {
                    do {
                        I n;
                        if (b){
                            n=ls;
                        }else {
                            n=rs;
                        }
                        if (s!=n){
                            currentTimeMillis=System.currentTimeMillis();
                        }
                        s=n;

                        for (int i=0;i<2;i++){
                            boolean b=i==0;
                            n=b?ls:rs;
                            JLabel jLabel=b?leftTime:rightTime;
                            long l=n.i / 60000;

                            StringBuilder m= new StringBuilder();
                            m.append(l);
                            m.insert(0,"0".repeat(2 - m.length()));

                            StringBuilder s=new StringBuilder();
                            s.append(n.i / 1000 - l * 60);
                            s.insert(0,"0".repeat(2 - s.length()));

                            StringBuilder ms=new StringBuilder();
                            ms.append(n.i % 1000);
                            ms.append("0".repeat(3 - ms.length()));

                            jLabel.setText(m+":"+s+"."+ms);
                        }

                        s.i=s.i-(System.currentTimeMillis()-currentTimeMillis);
                        currentTimeMillis=System.currentTimeMillis();
                        if (s.i <= (bout.start()* 1000L) / 2&&s.b) {
                            s.b=false;
                            try {
                                Clip clip = AudioSystem.getClip();
                                clip.open(AudioSystem.getAudioInputStream(new File("resource/media/Ring02.wav")));
                                clip.start();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                        if (s.i <= 0) {
                            s.i = 0;
                            if (bout.alternateSpeakers()) {
                                if (next.isEnabled()) {
                                    next.setEnabled(false);
                                    b = !b;
                                }
                            }
                            try {
                                Clip clip = AudioSystem.getClip();
                                clip.open(AudioSystem.getAudioInputStream(new File("resource/media/Alarm04.wav")));
                                clip.start();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } while (bout.alternateSpeakers() ? ls.i + rs.i > 0 : s.i > 0);
                    if (bout.alternateSpeakers()) {
                        leftTime.setText("00:00.000");
                        rightTime.setText("00:00.000");
                    } else {
                        if (b) {
                            leftTime.setText("00:00.000");
                        } else {
                            rightTime.setText("00:00.000");
                        }
                    }
                };
                Runnable runnable2 = () -> {
                    center.setVisible(false);
                    leftTime.setText("");
                    rightTime.setText("");
                    String s = current == Config.config.bouts().size() - 1 ? "本次辩论赛即将结束:" : "即将进入下一阶段:";
                    for (int i = bout.finishedWaitTime(); i >= 0; i--) {
                        stage.setText(s + i);
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                t = new Thread(runnable1);
                b = bout.prosFirst();
                if (bout.alternateSpeakers()) {
                    next.setVisible(true);
                    next.setEnabled(true);
                    t.start();
                    while (t.isAlive()) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } else {
                    next.setVisible(false);
                    t.start();
                    while (t.isAlive()) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (bout.prosFirst()) {
                        ls.i = 0;
                    } else {
                        rs.i = 0;
                    }
                    b = !b;
                    center.setVisible(false);
                    t = new Thread(() -> {
                        String s = "即将轮到另一方发言:";
                        for (int i = bout.finishedWaitTime() / 2; i >= 0; i--) {
                            stage.setText(s + i);
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                    t.start();
                    while (t.isAlive()) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    center.setVisible(true);
                    stage.setText("第" + (current + 1) + "辩" + (!bout.name().isEmpty() ? ":" + bout.name() : ""));
                    t = new Thread(runnable1);
                    t.start();
                    while (t.isAlive()) {
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                t = new Thread(runnable2);
                t.start();
                while (t.isAlive()){
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            MainInterface.mainInterface.setCurrent(MainInterface.mainInterface.welcome);
        });
        thread.start();
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
        buttons.add(finish);
        buttons.add(stop);
        buttons.add(jump);
        buttons.add(go);
        Style.setStyle(jPanels,buttons,null);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        pane=new JPanel(){
            protected void paintComponent(Graphics g) {
                g.drawImage(null, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        center=new JPanel(){
            protected void paintComponent(Graphics g) {
                g.drawImage(null, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
        down=new JPanel(){
            protected void paintComponent(Graphics g) {
                g.drawImage(null, 0, 0, this.getWidth(), this.getHeight(), this);
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
