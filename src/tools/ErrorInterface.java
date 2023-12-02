
package tools;

import data.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;

/**
 * @author wysha
 */
public class ErrorInterface extends JDialog {
    final boolean report;
    private JPanel contentPane;
    private JButton buttonOkay;
    private JTextArea textArea;
    private JScrollPane jScrollPane;
    final Throwable error;

    public ErrorInterface(String description, Throwable error, boolean report) {
        this.error=error;
        this.report=report;
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setText("遇到了一个异常:\n" + description + "\n" + error.toString());
        if (report){
            textArea.append("\n待用户确认后此错误将自动发送给开发者");
        }
        setContentPane(contentPane);
        setModal(true);
        setTitle("异常");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setStyle();
        setAlwaysOnTop(true);
        setSize((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3);
        setLocationRelativeTo(null);
        buttonOkay.addActionListener(e -> onOkay());
        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        contentPane.registerKeyboardAction(e -> onOkay(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(jScrollPane);
        buttons.add(buttonOkay);
        buttons.add(textArea);
        Style.setStyle(jPanels,buttons,null);
    }

    private void onOkay() {
        dispose();
        if (report){
            throw new RuntimeException(error);
        }
    }
}
