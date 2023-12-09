package main.views;

import data.Config;
import data.DebateNecessaryData;
import data.Style;
import main.Bout;
import main.Edit;
import main.MainInterface;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import tools.ErrorInterface;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.LinkedList;

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
        super.centerPanel = contentPane;
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
                    current[j] = DebateNecessaryData.deBateNecessaryData.configs.get(j);
                }
                delete.setEnabled(true);
                edit.setEnabled(false);
                out.setEnabled(true);
                use.setEnabled(false);
            }
        });
        flush();
        add.addActionListener(e -> {
            new Edit(null).setVisible(true);
            flush();
        });
        delete.addActionListener(e -> {
            for(Config config:current){
                DebateNecessaryData.deBateNecessaryData.configs.remove(config);
            }
            flush();
        });
        edit.addActionListener(e -> {
            new Edit(list.getSelectedValue()).setVisible(true);
            flush();
        });
        in.addActionListener(e -> {
            JFileChooser jFileChooser=new JFileChooser();
            jFileChooser.setFont(DebateNecessaryData.deBateNecessaryData.setting.font);
            jFileChooser.setFileFilter(new FileNameExtensionFilter("XML文件", "xml"));
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
                        LinkedList<Bout> bouts=new LinkedList<>();
                        SAXReader saxReader=new SAXReader();
                        Document document=saxReader.read(file);
                        Element root=document.getRootElement();
                        for (Element element:root.element("bouts").elements()){
                            bouts.add(new Bout(
                                    element.elementText("name"),
                                    Integer.parseInt(element.elementText("start")),
                                    Integer.parseInt(element.elementText("finishedWaitTime")),
                                    bouts
                            ));
                        }
                        Config config =new Config(
                                root.elementText("name"),
                                root.elementText("prosName"),
                                root.elementText("consName"),
                                root.elementText("thesis"),
                                bouts
                        );
                        for (Config f : DebateNecessaryData.deBateNecessaryData.configs) {
                            if (f.name().equals(config.name())) {
                                throw new RuntimeException("列表中已有同名配置");
                            }
                        }
                        DebateNecessaryData.deBateNecessaryData.configs.add(
                                config
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
            jFileChooser.setFont(DebateNecessaryData.deBateNecessaryData.setting.font);
            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (
                    jFileChooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION
            ){
                try {
                    for (Config config : current) {
                        StringBuilder stringBuilder=new StringBuilder();
                        stringBuilder
                                .append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n")
                                .append("<Config>\r\n")
                                .append("\t<name>").append(config.name()).append("</name>\r\n")
                                .append("\t<prosName>").append(config.prosName()).append("</prosName>\r\n")
                                .append("\t<consName>").append(config.consName()).append("</consName>\r\n")
                                .append("\t<thesis>").append(config.thesis()).append("</thesis>\r\n");
                        stringBuilder.append("\t<bouts>\r\n");
                        for (Bout bout:config.bouts()) {
                            stringBuilder.append("\t\t<bout>\r\n");
                            stringBuilder
                                    .append("\t\t\t<name>").append(bout.name()).append("</name>\r\n")
                                    .append("\t\t\t<start>").append(bout.start()).append("</start>\r\n")
                                    .append("\t\t\t<finishedWaitTime>").append(bout.finishedWaitTime()).append("</finishedWaitTime>\r\n");
                            stringBuilder.append("\t\t</bout>\r\n");
                        }
                        stringBuilder.append("\t</bouts>\r\n");
                        stringBuilder.append("</Config>\r\n");
                        File file=new File(jFileChooser.getSelectedFile().getPath() + "\\" + config.name() + ".xml");
                        if (file.exists()){
                            file.delete();
                        }
                        FileWriter fileWriter=new FileWriter(file,true);
                        fileWriter.write(stringBuilder.toString());
                        fileWriter.close();
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
            MainInterface.mainInterface.start.run();
            MainInterface.mainInterface.setCurrent(MainInterface.mainInterface.start);
        });
    }

    @Override
    public void flush() {
        super.flush();
        list.setListData(DebateNecessaryData.deBateNecessaryData.configs.toArray(new Config[0]));
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
            protected void paintComponent(Graphics g) {
                g.drawImage(null, 0, 0, this.getWidth(), this.getHeight(), this);
            }
        };
    }
}
