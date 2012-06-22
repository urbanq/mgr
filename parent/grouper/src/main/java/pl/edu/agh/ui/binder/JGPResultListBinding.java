package pl.edu.agh.ui.binder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.AbstractForm;
import org.valkyriercp.form.binding.swing.AbstractGlazedListsBinding;
import org.valkyriercp.widget.table.PropertyColumnTableDescription;
import org.valkyriercp.widget.table.TableDescription;
import pl.edu.agh.domain.JGPResult;
import pl.edu.agh.ui.JGPResultForm;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

/**
 * @author mateusz
 * @date 20.06.12
 */
public class JGPResultListBinding extends AbstractGlazedListsBinding {
    @Autowired
    private MessageSourceAccessor messages;

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
        desc.addPropertyColumn("jgp.productCode").withFixedWidth(120);
        desc.addPropertyColumn("value").withFixedWidth(100).withRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component result = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Font font = super.getFont();
                super.setFont(new Font(font.getFontName(), Font.BOLD, font.getSize() + 3));
                return result;
            };
            @Override
            protected void setValue(Object value) {
                setText((value == null) ? "" : value.toString() + " " + messages.getMessage("jgpResultListBinding.value.postfix", ""));
            }
        });
        return desc;
    }

    // detail form behavior
    @Override
    protected AbstractForm createDetailForm() {
        AbstractForm form= new JGPResultForm();
        form.getFormModel().setReadOnly(true);
        return form;
    }
}
