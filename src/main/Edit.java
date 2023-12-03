package main;

import data.Config;
import data.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;

/**
 * @author wysha
 */
public class Edit extends JDialog {
    private JPanel contentPane;
    private JButton buttonOkay;
    private JButton buttonCancel;
    private JPanel down;
    private JPanel up;
    public Edit(Config config) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOkay);

        buttonOkay.addActionListener(e -> onOkay());

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
        setVisible(true);
    }

    private void flush(){
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        Style.setStyle(jPanels,buttons,null);
    }

    private void onOkay() {
        // 在此处添加您的代码
        dispose();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }
}
