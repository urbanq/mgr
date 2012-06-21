package pl.edu.agh.ui.binder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.AbstractForm;
import org.valkyriercp.form.binding.swing.AbstractGlazedListsBinding;
import org.valkyriercp.widget.table.PropertyColumnTableDescription;
import org.valkyriercp.widget.table.TableDescription;
import pl.edu.agh.domain.JGPResult;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * @author mateusz
 * @date 20.06.12
 */
public class JGPResultListBinding extends AbstractGlazedListsBinding {
    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    public JGPResultListBinding(FormModel formModel, String formPropertyPath) {
        super(formModel, formPropertyPath);
        setDialogId("jgpResultListBindingDialog");
        setAddSupported(false);
        setEditSupported(false);
        setRemoveSupported(false);
        setShowDetailSupported(true);
    }

    @Override
    protected TableDescription getTableDescription() {
        PropertyColumnTableDescription desc = new PropertyColumnTableDescription("jgpResultListBinding", JGPResult.class);
        desc.addPropertyColumn("jgp.code").withFixedWidth(70);
        desc.addPropertyColumn("jgp.name");
        desc.addPropertyColumn("value").withFixedWidth(100).withRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Font font = super.getFont();
                super.setFont(new Font(font.getName(), Font.BOLD, font.getSize() + 1));
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            };
            @Override
            protected void setValue(Object value) {
                setText((value == null) ? "" : value.toString() + " " + messageSourceAccessor.getMessage("jgpResultListBinding.value.postfix", ""));
            }
        });
        return desc;
    }

    // detail form behavior

    @Override
    protected Object getNewFormObject() {
        return null;//new ICD10Wrapper();
    }

    @Override
    protected AbstractForm createAddEditForm() {
        return null;//new ICD10WrapperForm();
    }

    @Override
    protected AbstractForm createDetailForm() {
//        AbstractForm f = new ICD10WrapperForm();
//        f.getFormModel().setReadOnly(true);
//        return f;
        return null;
    }
}
