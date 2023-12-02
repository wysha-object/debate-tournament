
package tools;

import data.Style;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.List;

/**
 * @author wysha
 */
public class GetAndSetList extends JDialog {
    private final List<Object> data;
    private final List<Object> chooseDate;
    private JPanel contentPane;
    private JButton buttonOkay;
    private JButton buttonCancel;
    private JPanel down;
    private JScrollPane right;
    private JList<Object> list;
    private JList<Object> chooseList;
    private JLabel leftLabel;
    private JLabel rightLabel;
    private JButton add;
    private JButton remove;
    private JLabel jLabel;
    private JPanel panel;

    public GetAndSetList(String text, List<Object> data, List<Object> choose) {
        setTitle(text);
        this.data = data;
        chooseDate=choose;
        list.setListData(this.data.toArray());
        if (choose!=null) {
            add.setEnabled(false);
            remove.setEnabled(false);
            list.addListSelectionListener(e -> add.setEnabled(list.getSelectedIndex() != -1));
            chooseList.addListSelectionListener(e -> remove.setEnabled(chooseList.getSelectedIndex() != -1));
            add.addActionListener(e -> {
                int[] ints=list.getSelectedIndices();
                if (ints.length==1){
                    Object o=list.getSelectedValue();
                    this.data.remove(o);
                    chooseDate.add(o);
                }else {
                    for (int j = ints.length-1; j >=0; j--) {
                        int i = ints[j];
                        chooseDate.add(this.data.remove(i));
                    }
                }
                list.setListData(this.data.toArray());
                chooseList.setListData(chooseDate.toArray());
            });
            remove.addActionListener(e -> {
                int[] ints=chooseList.getSelectedIndices();
                if (ints.length==1){
                    Object o=chooseList.getSelectedValue();
                    this.data.add(o);
                    chooseDate.remove(o);
                }else {
                    for (int j = ints.length-1; j >=0; j--) {
                        int i = ints[j];
                        this.data.add(chooseDate.remove(i));
                    }
                }
                list.setListData(this.data.toArray());
                chooseList.setListData(chooseDate.toArray());
            });
            chooseList.setListData(chooseDate.toArray());
        }else {
            leftLabel.setVisible(false);
            rightLabel.setVisible(false);
            right.setVisible(false);
            panel.setVisible(false);
        }
        setContentPane(contentPane);
        setModal(true);
        jLabel.setText(text);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setStyle();
        buttonOkay.addActionListener(e -> onOkay());
        buttonCancel.addActionListener(e -> onCancel());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        contentPane.registerKeyboardAction(e -> onOkay(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }
    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        HashSet<JList<?>> jLists = new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(down);
        jPanels.add(right);
        jPanels.add(panel);
        buttons.add(buttonOkay);
        buttons.add(buttonCancel);
        buttons.add(leftLabel);
        buttons.add(rightLabel);
        buttons.add(add);
        buttons.add(remove);
        buttons.add(jLabel);
        jLists.add(list);
        jLists.add(chooseList);
        Style.setStyle(jPanels,buttons,jLists);
    }
    public List<Object> getChoose(){
        return chooseDate;
    }

    private void onOkay() {
        dispose();
    }
    private void onCancel() {
        data.addAll(chooseDate);
        if (!chooseDate.isEmpty()) {
            chooseDate.clear();
        }
        dispose();
    }
}