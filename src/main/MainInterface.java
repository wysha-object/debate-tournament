package main;

import data.DebateNecessaryData;
import data.Style;
import main.views.Start;
import main.views.View;
import main.views.Welcome;
import set.NecessarySet;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

import static java.lang.Thread.sleep;

/**
 * @author wysha
 */
public class MainInterface extends JFrame {
    final CardLayout cardLayout=new CardLayout();
    protected View current;
    public void setCurrent(View abstractSubpages){
        cardLayout.show(show,abstractSubpages.viewName);
        repaint();
        current=abstractSubpages;
    }
    final public Welcome welcome =new Welcome();
    final public Start start=new Start();
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
    private JPanel down;
    Thread thread;
    public MainInterface() throws Throwable {
        mainInterface=this;
        setUndecorated(true);
        setSize(
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
        );
        show.setLayout(cardLayout);
        class MyJFrame extends JFrame {
            MyJFrame() {
                JPanel jPanel = new JPanel();
                JLabel jLabel = new JLabel("开发者:熊锦枫", JLabel.CENTER);
                JLabel mail = new JLabel("开发者邮箱:wyshazhisishen@yeah.net", JLabel.CENTER);
                jLabel.setFont(new Font("微软雅黑", Font.PLAIN, 72));
                mail.setFont(new Font("微软雅黑", Font.PLAIN, 72));
                GridLayout gridLayout = new GridLayout(2, 1);
                jPanel.setLayout(gridLayout);
                jPanel.add(jLabel, BorderLayout.NORTH);
                jPanel.add(mail, BorderLayout.SOUTH);
                add(jPanel);
                jPanel.setBackground(Color.BLACK);
                jLabel.setForeground(Color.WHITE);
                mail.setForeground(Color.WHITE);
                setUndecorated(true);
                setSize(
                        (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                        (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()
                );
                setAlwaysOnTop(true);
            }
        }
        MyJFrame jFrame = new MyJFrame();
        jFrame.setVisible(true);
        for (float i=0;i<1;i+= 0.001F){
            sleep(1);
            jFrame.setOpacity(i);
        }
        sleep(1000);
        setVisible(true);
        thread=new Thread(() -> {
            while(true) {
                MainInterface.mainInterface.flush();
            }
        });
        thread.start();
        setContentPane(contentPane);
        show.add(welcome.centerPanel, welcome.viewName);
        show.add(start.centerPanel,start.viewName);
        setCurrent(welcome);
        for (float i=1;i>0;i-= 0.001F){
            sleep(1);
            jFrame.setOpacity(i);
        }
        jFrame.dispose();
        exit.addActionListener(e -> {
            try {
                DebateNecessaryData.deBateNecessaryData.write();
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }
            MainInterface.mainInterface.setEnabled(false);
            new Thread(() -> {
                MyJFrame frame = new MyJFrame();
                frame.setVisible(true);
                for (float i = 0; i < 1; i += 0.001F) {
                    try {
                        sleep(1);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.setOpacity(i);
                }
                MainInterface.mainInterface.dispose();
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                for (float i = 1; i > 0; i -= 0.001F) {
                    try {
                        sleep(1);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.setOpacity(i);
                }
                System.exit(0);
            }).start();
        });
        set.addActionListener(e -> {
            NecessarySet necessarySet=new NecessarySet();
            necessarySet.setVisible(true);
            flush();
        });
        flush.addActionListener(e -> {
            thread.stop();
            thread=new Thread(() -> {
                while(true) {
                    MainInterface.mainInterface.flush();
                }
            });
            thread.start();
        });
    }
    public static void main(String[] args) throws Throwable {
        DebateNecessaryData.deBateNecessaryData.read();
        new MainInterface();
    }

    private void flush(){
        setStyle();
        repaint();
        if (current!=null){
            current.centerPanel.repaint();
        }
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(down);
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