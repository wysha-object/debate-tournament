
package data;

import javax.swing.*;
import java.io.Serializable;
import java.util.HashSet;

/**
 * @author wysha
 */
public final class Style implements Serializable {
    public static void setStyle(HashSet<JComponent> jPanels, HashSet<JComponent> buttons, HashSet<JList<?>> jLists) {
        if (jPanels != null) {
            for (JComponent j : jPanels) {
                j.setBackground(NecessaryData.necessaryData.setting.jPanelBackground);
                j.setForeground(NecessaryData.necessaryData.setting.foreground);
                j.setFont(NecessaryData.necessaryData.setting.font);
            }
        }
        if (buttons != null) {
            for (JComponent b : buttons) {
                b.setBackground(NecessaryData.necessaryData.setting.background);
                b.setForeground(NecessaryData.necessaryData.setting.foreground);
                b.setFont(NecessaryData.necessaryData.setting.font);
            }
        }
        if (jLists != null) {
            for (JList<?> jList : jLists) {
                jList.setBackground(NecessaryData.necessaryData.setting.background);
                jList.setForeground(NecessaryData.necessaryData.setting.foreground);
                jList.setFont(NecessaryData.necessaryData.setting.font);
                jList.setSelectionBackground(NecessaryData.necessaryData.setting.foreground);
                jList.setSelectionForeground(NecessaryData.necessaryData.setting.jPanelBackground);
            }
        }
    }
}
