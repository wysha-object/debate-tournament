package main;

import data.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * @author wysha
 */
public class BoutEdit extends JDialog {
    public Bout value;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPanel up;
    private JPanel down;
    private JSpinner setSS;
    private JSpinner setWT;
    private JLabel l1;
    private JLabel l3;
    private JLabel l4;
    private JTextField textField1;
    private final LinkedList<Bout> bouts;
    public BoutEdit(Bout bout, LinkedList<Bout> bouts) {
        this.bouts=bouts;
        setUndecorated(true);
        setContentPane(contentPane);
        setModal(true);
        if (bout!=null){
            textField1.setText(bout.name());
            setSS.setValue(bout.start());
            setWT.setValue(bout.finishedWaitTime());
        }
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(e -> onOK());

        buttonCancel.addActionListener(e -> onCancel());

        // 点击 X 时调用 onCancel()
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // 遇到 ESCAPE 时调用 onCancel()
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setSize(
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()*2/3,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()*2/3
        );
        setLocationRelativeTo(null);
        flush();
    }

    private void onOK() {
        value= new Bout(
                textField1.getText(),
                (int) setSS.getValue(),
                (int) setWT.getValue(),
                bouts
        );
        // 在此处添加您的代码
        dispose();
    }

    private void flush(){
        setStyle();
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        HashSet<JList<?>> lists=new HashSet<>();
        jPanels.add(up);
        jPanels.add(down);
        buttons.add(buttonCancel);
        buttons.add(buttonOK);
        buttons.add(l1);
        buttons.add(l3);
        buttons.add(l4);
        buttons.add(textField1);
        buttons.add(setSS);
        buttons.add(setWT);
        Style.setStyle(jPanels,buttons,lists);
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }
}
