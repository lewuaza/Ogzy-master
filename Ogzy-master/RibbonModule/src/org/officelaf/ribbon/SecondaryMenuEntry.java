package org.officelaf.ribbon;

import org.jvnet.flamingo.common.JCommandButton;
import org.jvnet.flamingo.common.icon.ResizableIcon;
import org.jvnet.flamingo.ribbon.RibbonApplicationMenuEntrySecondary;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SecondaryMenuEntry extends RibbonApplicationMenuEntrySecondary {
    public SecondaryMenuEntry(Action action) {
        this(ActionUtil.lookupText(action), ActionUtil.lookupDescription(action), ActionUtil.lookupIcon(action, false),
                action);
    }

    public SecondaryMenuEntry(String text, String description, ResizableIcon icon, final Action action) {
        super(icon, text, action, JCommandButton.CommandButtonKind.ACTION_ONLY);
        if (action != null) {
            action.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent evt) {
                    if ("enabled".equals(evt.getPropertyName())) {
                        setEnabled(action.isEnabled());
                    }
                }
            });
            setEnabled(action.isEnabled());
        }
        setDescriptionText(description);
    }
}
