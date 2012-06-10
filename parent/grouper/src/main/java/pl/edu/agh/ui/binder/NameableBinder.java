package pl.edu.agh.ui.binder;

import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.binding.swing.AbstractListBinding;
import org.valkyriercp.form.binding.swing.ComboBoxBinder;
import org.valkyriercp.form.binding.swing.ComboBoxBinding;
import pl.edu.agh.dao.ReadDao;
import pl.edu.agh.domain.Nameable;

import javax.swing.*;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public class NameableBinder<T extends Nameable> extends ComboBoxBinder {
    private ReadDao<T, ?> dao;

    public NameableBinder(Class<T> clazz) {
        super(clazz, new String[] { SELECTABLE_ITEMS_KEY, COMPARATOR_KEY, RENDERER_KEY, EDITOR_KEY, FILTER_KEY,
                EMPTY_SELECTION_VALUE });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AbstractListBinding createListBinding(JComponent control, FormModel formModel,
                                                    String formPropertyPath)
    {
        ComboBoxBinding binding = (ComboBoxBinding) super.createListBinding((JComboBox) control, formModel,
                formPropertyPath);
        binding.setSelectableItems(dao.getAll());
        binding.setRenderer(new NameableListCellRenderer());
        return binding;
    }

    public void setDao(ReadDao<T, ?> dao) {
        this.dao = dao;
    }
}
