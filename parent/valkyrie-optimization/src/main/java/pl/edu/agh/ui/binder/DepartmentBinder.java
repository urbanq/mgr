package pl.edu.agh.ui.binder;

import org.springframework.beans.factory.annotation.Autowired;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.binding.swing.AbstractListBinding;
import org.valkyriercp.form.binding.swing.ComboBoxBinder;
import org.valkyriercp.form.binding.swing.ComboBoxBinding;
import pl.edu.agh.dao.DepartmentDao;
import pl.edu.agh.domain.Department;

import javax.swing.*;
import java.awt.*;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class DepartmentBinder extends ComboBoxBinder {
    @Autowired
    private DepartmentDao departmentDao;

    public DepartmentBinder() {
        super(Department.class, new String[] { SELECTABLE_ITEMS_KEY, COMPARATOR_KEY, RENDERER_KEY, EDITOR_KEY, FILTER_KEY,
                EMPTY_SELECTION_VALUE });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AbstractListBinding createListBinding(JComponent control, FormModel formModel,
                                                    String formPropertyPath)
    {
        ComboBoxBinding binding = (ComboBoxBinding) super.createListBinding((JComboBox) control, formModel,
                formPropertyPath);
        binding.setSelectableItems(departmentDao.getAll());
        binding.setRenderer(new DepartmentListCellRenderer());
        return binding;
    }

    private class DepartmentListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if(value instanceof Department) {
                setText(((Department)value).getName());
            }
            return this;
        }
    }
}
