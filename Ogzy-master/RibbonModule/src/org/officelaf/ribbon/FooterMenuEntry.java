package org.officelaf.ribbon;

import org.jvnet.flamingo.ribbon.RibbonApplicationMenuEntryFooter;

import javax.swing.*;

public class FooterMenuEntry extends RibbonApplicationMenuEntryFooter {
    public FooterMenuEntry(Action action) {
        super(ActionUtil.lookupIcon(action, false), ActionUtil.lookupText(action), action);
    }
}
