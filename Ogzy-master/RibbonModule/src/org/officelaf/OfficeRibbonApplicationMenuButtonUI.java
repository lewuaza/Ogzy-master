package org.officelaf;

import org.jvnet.flamingo.common.CommandButtonLayoutManager;
import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.model.PopupButtonModel;
import org.jvnet.flamingo.common.popup.JPopupPanel;
import org.jvnet.flamingo.common.popup.PopupPanelCallback;
import org.jvnet.flamingo.ribbon.JRibbon;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenu;
import org.jvnet.flamingo.ribbon.ui.appmenu.BasicRibbonApplicationMenuButtonUI;
import org.jvnet.flamingo.ribbon.ui.appmenu.JRibbonApplicationMenuButton;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import java.awt.*;
import org.gui.AboutPanel;
import org.gui.MainTopComponent;
import org.openide.windows.TopComponent;

public class OfficeRibbonApplicationMenuButtonUI extends BasicRibbonApplicationMenuButtonUI {

    public static ComponentUI createUI(JComponent c) {
        return new OfficeRibbonApplicationMenuButtonUI();
    }

    @Override
    protected void installComponents() {
        super.installComponents();

        final JRibbonApplicationMenuButton appMenuButton = (JRibbonApplicationMenuButton) this.commandButton;
        appMenuButton.setPopupCallback(new PopupPanelCallback() {

            @Override
            public JPopupPanel getPopupPanel(final JCommandButton commandButton) {
                
                TopComponent top = TopComponent.getRegistry().getActivated();
                
                AboutPanel about_panel = new AboutPanel();
                
                Object[] close_button = {"Wróć do programu"}; 
                
                JOptionPane.showOptionDialog(top, about_panel, "O Aplikacji...", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, close_button, close_button[0]);
                return null;
            }
        });
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        Graphics2D g2d = (Graphics2D) g.create();
        Insets ins = c.getInsets();
        Rectangle backgroundRect = new Rectangle(ins.left, ins.top, c.getWidth()
                - ins.left - ins.right, c.getHeight() - ins.top - ins.bottom);
        this.paintButtonBackground(g2d, backgroundRect);
        CommandButtonLayoutManager.CommandButtonLayoutInfo layoutInfo = this.layoutManager.getLayoutInfo(this.commandButton, g);
        commandButton.putClientProperty("icon.bounds", layoutInfo.iconRect);
        this.actionClickArea = new Rectangle(0, 0, 0, 0);
        this.popupClickArea = layoutInfo.popupClickArea;
        this.paintButtonIcon(g2d, backgroundRect);
        g2d.dispose();
    }

    
    private static final ImageIcon normal;
    private static final ImageIcon over;
    private static final ImageIcon down;

    static {
        // TODO These need to be provided somehow
        normal = new ImageIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("images/exie_officebutton.png"));
        over   = new ImageIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("images/exie_officebutton_over.png"));
        down   = new ImageIcon(OfficeRibbonApplicationMenuButtonUI.class.getResource("images/exie_officebutton_down.png"));
    }

    @Override
    protected void configureRenderer() {
        // not using the renderer, but instantiating it to prevent NPEs in the super class
        this.buttonRendererPane = new CellRendererPane();
        this.rendererButton = new JButton("");
    }

    @Override
    protected void unconfigureRenderer() {
        this.buttonRendererPane = null;
        this.rendererButton = null;
    }

    @Override
    protected void paintButtonBackground(Graphics graphics, Rectangle toFill) {
        // No background
    }

    @Override
    protected Icon getIconToPaint() {
        PopupButtonModel model = this.applicationMenuButton.getPopupModel();
        Icon icon;
        if (model.isPressed() || model.isPopupShowing()) {
            icon = down;
        } else if (model.isRollover()) {
            icon = over;
        } else {
            icon = normal;
        }
        return icon;
    }
}
