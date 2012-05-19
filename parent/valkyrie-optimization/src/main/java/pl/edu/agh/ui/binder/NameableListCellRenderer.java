package pl.edu.agh.ui.binder;

import pl.edu.agh.domain.Nameable;

import javax.swing.*;
import java.awt.*;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public class NameableListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if(value instanceof Nameable) {
            String name = ((Nameable)value).getName();
//            if (name.length() > 40) {
//                name = name.substring(0, 39);
//            }
            setText(name);
        }
        return this;
    }
}
