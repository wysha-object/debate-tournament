
package set;

import data.NecessaryData;
import data.Style;
import tools.ErrorInterface;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

/**
 * @author wysha
 */
public class NecessarySet extends Set {
    private JPanel contentPane;
    private JButton buttonOkay;
    private JPanel right;
    private JButton buttonCancel;
    private JPanel up;
    private JPanel down;

    public NecessarySet() {
        super.show = right;
        setContentPane(contentPane);
        right.setLayout(cardLayout);
        right.add(defaultPage.contentPane,defaultPage.name);
        setCurrent(defaultPage);
        setSize(
                (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()*2/3,
                (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()*2/3
        );
        setLocationRelativeTo(null);
        buttonOkay.addActionListener(ee -> {
            if (current!=defaultPage){
                current.onOkay();
                try {
                    NecessaryData.necessaryData.write();
                } catch (Throwable e) {
                    new ErrorInterface(
                            "写入失败",
                            e,
                            false
                    ).setVisible(true);
                }
            }
        });
        buttonCancel.addActionListener(ee -> defaultPage.onCancel());
        setModal(true);
        setTitle("核心设置");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setStyle();
        buttonOkay.addActionListener(ee -> dispose());
        buttonCancel.addActionListener(ee -> dispose());
        buttonOkay.addActionListener(e -> current.onOkay());
        buttonCancel.addActionListener(e -> current.onCancel());
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(up);
        jPanels.add(down);
        jPanels.add(right);
        buttons.add(buttonOkay);
        buttons.add(buttonCancel);
        Style.setStyle(jPanels,buttons,null);
    }
}
