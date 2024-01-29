
package cn.com.wysha.debate_tournament.data;

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
                j.setBackground(DebateNecessaryData.deBateNecessaryData.setting.jPanelBackground);
                j.setForeground(DebateNecessaryData.deBateNecessaryData.setting.foreground);
                j.setFont(DebateNecessaryData.deBateNecessaryData.setting.font);
            }
        }
        if (buttons != null) {
            for (JComponent b : buttons) {
                b.setBackground(DebateNecessaryData.deBateNecessaryData.setting.background);
                b.setForeground(DebateNecessaryData.deBateNecessaryData.setting.foreground);
                b.setFont(DebateNecessaryData.deBateNecessaryData.setting.font);
            }
        }
        if (jLists != null) {
            for (JList<?> jList : jLists) {
                jList.setBackground(DebateNecessaryData.deBateNecessaryData.setting.background);
                jList.setForeground(DebateNecessaryData.deBateNecessaryData.setting.foreground);
                jList.setFont(DebateNecessaryData.deBateNecessaryData.setting.font);
                jList.setSelectionBackground(DebateNecessaryData.deBateNecessaryData.setting.foreground);
                jList.setSelectionForeground(DebateNecessaryData.deBateNecessaryData.setting.jPanelBackground);
            }
        }
    }
}
