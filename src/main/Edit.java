package main;

import data.Config;
import data.DebateNecessaryData;
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
public class Edit extends JDialog {
    private Bout[] current;
    LinkedList<Bout> bouts=new LinkedList<>();
    private JPanel contentPane;
    private JButton buttonOkay;
    private JButton buttonCancel;
    private JPanel down;
    private JPanel r;
    private JButton edit;
    private JList<Bout> list;
    private JButton add;
    private JLabel label;
    private JPanel l;
    private JTextField textField1;
    private JTextField setPros;
    private JTextField setCons;
    private JButton delete;
    private JScrollPane jsp;
    private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JPanel up;
    private JTextField setName;
    private JLabel jLabel;
    private JButton upMove;
    private JButton downMove;

    public Edit(Config config) {
        setName.setText("配置");
        setUndecorated(true);
        if (config!=null){
            bouts=new LinkedList<>(config.bouts());
            for (int i = 0, boutsSize = bouts.size(); i < boutsSize; i++) {
                Bout bout = bouts.get(i);
                bouts.remove(i);
                bouts.add(
                        i,
                        new Bout(
                        bout.name(),
                        bout.start(),
                        bout.finishedWaitTime(),
                        bouts
                ));
            }
            setPros.setText(config.prosName());
            setCons.setText(config.consName());
            setName.setText(config.name());
            textField1.setText(config.thesis());
        }
        delete.setEnabled(false);
        edit.setEnabled(false);
        upMove.setEnabled(false);
        downMove.setEnabled(false);
        setContentPane(contentPane);
        setModal(true);
        list.addListSelectionListener(e -> {
            if (list.getSelectedIndex() == -1) {
                delete.setEnabled(false);
                edit.setEnabled(false);
                upMove.setEnabled(false);
                downMove.setEnabled(false);
            } else if (list.getSelectedIndices().length==1){
                current = new Bout[1];
                current[0] = list.getSelectedValue();
                delete.setEnabled(true);
                edit.setEnabled(true);
                upMove.setEnabled(true);
                downMove.setEnabled(true);
            }else {
                current = new Bout[list.getSelectedIndices().length];
                int[] selectedIndices = list.getSelectedIndices();
                for (int j = 0; j < selectedIndices.length; j++) {
                    current[j] = bouts.get(selectedIndices[j]);
                }
                delete.setEnabled(true);
                edit.setEnabled(false);
                upMove.setEnabled(true);
                downMove.setEnabled(true);
            }
        });
        delete.addActionListener(e -> {
            for(Bout bout:current){
                bouts.remove(bout);
            }
            flush();
        });
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
        add.addActionListener(e -> {
           BoutEdit boutEdit=new BoutEdit(null,this.bouts);
           boutEdit.setVisible(true);
            if (boutEdit.value!=null){
                bouts.add(boutEdit.value);
            }
           flush();
        });
        edit.addActionListener(e -> {
            BoutEdit boutEdit=new BoutEdit(current[0],this.bouts);
            boutEdit.setVisible(true);
            if (boutEdit.value!=null){
                bouts.add(boutEdit.value);
            }
            flush();
        });
        upMove.addActionListener(e -> {
            for (Bout bout:current){
                for (int i=0;i<bouts.size();i++){
                    if (bout==bouts.get(i)&&i-1>0){
                        bouts.remove(i);
                        bouts.add(i-1,bout);
                        break;
                    }
                }
            }
            flush();
        });
        downMove.addActionListener(e -> {
            for (Bout bout:current){
                for (int i=0;i<bouts.size();i++){
                    if (bout==bouts.get(i)&&i+1<bouts.size()){
                        bouts.remove(i);
                        bouts.add(i+1,bout);
                        break;
                    }
                }
            }
            flush();
        });
    }

    private void flush(){
        setStyle();
        list.setListData(bouts.toArray(new Bout[0]));
    }

    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        HashSet<JList<?>> lists=new HashSet<>();
        jPanels.add(l);
        jPanels.add(r);
        jPanels.add(down);
        jPanels.add(jsp);
        jPanels.add(up);
        buttons.add(buttonCancel);
        buttons.add(buttonOkay);
        buttons.add(label);
        buttons.add(l1);
        buttons.add(l2);
        buttons.add(l3);
        buttons.add(textField1);
        buttons.add(setPros);
        buttons.add(setCons);
        buttons.add(setName);
        buttons.add(add);
        buttons.add(edit);
        buttons.add(delete);
        buttons.add(jLabel);
        buttons.add(upMove);
        buttons.add(downMove);
        lists.add(list);
        Style.setStyle(jPanels,buttons,lists);
    }

    private void onOkay() {
        DebateNecessaryData.deBateNecessaryData.configs.add(new Config(
                setName.getText(),
                setPros.getText(),
                setCons.getText(),
                textField1.getText(),
                bouts
        ));
        // 在此处添加您的代码
        dispose();
    }

    private void onCancel() {
        // 必要时在此处添加您的代码
        dispose();
    }
}
