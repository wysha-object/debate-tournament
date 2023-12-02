
package tools;

import data.Style;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.HashSet;

/**
 * @author wysha
 */
public class GetOutCome extends JDialog {
    private JPanel contentPane;
    private JButton buttonOkay;
    private JLabel jLabel;

    public GetOutCome(String title, String s) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jLabel.setText(s);
        setContentPane(contentPane);
        setModal(true);
        setTitle(title);
        setStyle();
        buttonOkay.addActionListener(e -> onOkay());
        contentPane.registerKeyboardAction(e -> dispose(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        contentPane.registerKeyboardAction(e -> onOkay(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        buttons.add(buttonOkay);
        buttons.add(jLabel);
        Style.setStyle(jPanels,buttons,null);
    }

    private void onOkay() {
        dispose();
    }
}
