package main.views;

import data.Config;
import data.NecessaryData;
import data.Style;
import main.Edit;
import main.MainInterface;
import tools.ErrorInterface;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;

/**
 * @author wysha
 */
public class Welcome extends View {
    private Config[] current;
    private JPanel contentPane;
    private JButton add;
    private JButton edit;
    private JScrollPane js;
    private JButton delete;
    private JLabel label;
    private JList<Config> list;
    private JButton in;
    private JButton out;
    private JButton use;

    public Welcome() {
        super(Welcome.class.toString());
        NecessaryData.necessaryData.configs.add(new Config("1","正方l","反方r"));
        super.jPanel= contentPane;
        delete.setEnabled(false);
        edit.setEnabled(false);
        out.setEnabled(false);
        use.setEnabled(false);
        list.addListSelectionListener(e -> {
            if (list.getSelectedIndex() == -1) {
                delete.setEnabled(false);
                edit.setEnabled(false);
                out.setEnabled(false);
                use.setEnabled(false);
            } else if (list.getSelectedIndices().length==1){
                current = new Config[1];
                current[0] = list.getSelectedValue();
                delete.setEnabled(true);
                edit.setEnabled(true);
                out.setEnabled(true);
                use.setEnabled(true);
            }else {
                current = new Config[list.getSelectedIndices().length];
                int[] selectedIndices = list.getSelectedIndices();
                for (int j = 0; j < selectedIndices.length; j++) {
                    current[j] = NecessaryData.necessaryData.configs.get(j);
                }
                delete.setEnabled(true);
                edit.setEnabled(false);
                out.setEnabled(true);
                use.setEnabled(false);
            }
        });
        flush();
        add.addActionListener(e -> {
            flush();
            new Edit(null);
        });
        delete.addActionListener(e -> {
            for(Config config:current){
                NecessaryData.necessaryData.configs.remove(config);
            }
            flush();
        });
        edit.addActionListener(e -> {
            flush();
            new Edit(list.getSelectedValue());
        });
        in.addActionListener(e -> {
            JFileChooser jFileChooser=new JFileChooser();
            jFileChooser.setFont(NecessaryData.necessaryData.setting.font);
            jFileChooser.setFileFilter(new FileNameExtensionFilter("辩论赛配置文件", "DeBateConfig"));
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.setMultiSelectionEnabled(true);
            if (
                    jFileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION
            ){
                File[] selectedFiles=jFileChooser.getSelectedFiles();
                if (selectedFiles.length==0){
                    selectedFiles=new File[]{jFileChooser.getSelectedFile()};
                }
                for (File file:selectedFiles){
                    try {
                        Config abstractFunction =
                                (Config)
                                        new ObjectInputStream(
                                                Files.newInputStream(
                                                        file.toPath()
                                                )
                                        ).readObject();
                        for (Config f : NecessaryData.necessaryData.configs) {
                            if (f.name.equals(abstractFunction.name)) {
                                throw new RuntimeException("列表中已有同名配置");
                            }
                        }
                        NecessaryData.necessaryData.configs.add(
                                abstractFunction
                        );
                    }catch (Exception exception){
                        new ErrorInterface(
                                file+"读取失败",
                                exception,
                                false
                        ).setVisible(true);
                    }
                }
                flush();
            }
        });
        out.addActionListener(e -> {
            JFileChooser jFileChooser=new JFileChooser();
            jFileChooser.setFont(NecessaryData.necessaryData.setting.font);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (
                    jFileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION
            ){
                try {
                    for (Config config : current) {
                        new ObjectOutputStream(
                                Files.newOutputStream(Paths.get(jFileChooser.getSelectedFile().getPath() + "\\" + config.name + ".DeBateConfig"))
                        ).writeObject(config);
                    }
                    ProcessBuilder processBuilder = new ProcessBuilder("explorer", jFileChooser.getSelectedFile().getPath());
                    processBuilder.start();
                }catch (Exception exception){
                    new ErrorInterface(
                            "写入失败",
                            exception,
                            false
                    ).setVisible(true);
                }
            }
        });
        use.addActionListener(e -> {
            Config.config=current[0];
            MainInterface.mainInterface.start.flush();
            MainInterface.mainInterface.setCurrent(MainInterface.mainInterface.start);
        });
    }

    @Override
    public void flush() {
        super.flush();
        list.setListData(NecessaryData.necessaryData.configs.toArray(new Config[0]));
    }

    @Override
    public void setStyle() {
        HashSet<JComponent> jPanels = new HashSet<>();
        HashSet<JComponent> buttons = new HashSet<>();
        HashSet<JList<?>> jLists=new HashSet<>();
        jPanels.add(contentPane);
        jPanels.add(js);
        buttons.add(label);
        buttons.add(add);
        buttons.add(delete);
        buttons.add(edit);
        buttons.add(in);
        buttons.add(out);
        buttons.add(use);
        jLists.add(list);
        Style.setStyle(jPanels,buttons,jLists);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        contentPane=new JPanel(){
            private final Image image = new ImageIcon("").getImage();
            protected void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
    }
}
