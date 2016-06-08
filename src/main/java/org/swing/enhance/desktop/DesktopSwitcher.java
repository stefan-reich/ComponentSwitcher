package org.swing.enhance.desktop;

import org.swing.enhance.switching.SwitchDialog;
import org.swing.enhance.switching.Switcher;

import javax.swing.*;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.List;

public class DesktopSwitcher implements Switcher<JInternalFrame> {

    private final JDesktopPane jDesktopPane;
    private SwitchDialog<JInternalFrame> switchDialog;

    public DesktopSwitcher(JDesktopPane jDesktopPane) {
        this.jDesktopPane = jDesktopPane;
        switchDialog = new SwitchDialog<>(SwingUtilities.getWindowAncestor(jDesktopPane), new ArrayList<JInternalFrame>());
    }

    public void previous(List<JInternalFrame> switchableComponentList) {
        showMenu(switchableComponentList);
        switchDialog.selectPrevious();
    }

    public void next(List<JInternalFrame> switchableComponentList) {
        showMenu(switchableComponentList);
        switchDialog.selectNext();
    }

    public void dismiss() {
        hideMenu();
    }

    private void showMenu(List<JInternalFrame> switchableComponentList) {
        switchDialog.setSwitchableComponents(switchableComponentList);
        if (!switchableComponentList.isEmpty()) {
            switchDialog.pack();
            switchDialog.setLocationRelativeTo(jDesktopPane);
            switchDialog.setVisible(true);
        }
    }

    private void hideMenu() {
        if (switchDialog != null) {
            try {
                JInternalFrame selected = switchDialog.getSelected();
                if (selected != null) {
                    selected.setSelected(true);
                }
            } catch (PropertyVetoException e) {
                //todo use a logger
                e.printStackTrace();
            }
            switchDialog.dispose();
        }
    }

    public SwitchDialog<JInternalFrame> getSwitchDialog() {
        return switchDialog;
    }
}
