package org.officelaf;

import org.jvnet.flamingo.ribbon.ui.BasicRibbonGalleryUI;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;

public class OfficeRibbonGalleryUI extends BasicRibbonGalleryUI {
    public static ComponentUI createUI(JComponent c) {
        return new OfficeRibbonGalleryUI();
    }
}