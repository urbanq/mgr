package pl.edu.agh.ui.binder;

import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.AbstractForm;
import org.valkyriercp.form.binding.swing.AbstractGlazedListsBinding;
import org.valkyriercp.widget.table.PropertyColumnTableDescription;
import org.valkyriercp.widget.table.TableDescription;
import pl.edu.agh.domain.Stay;
import pl.edu.agh.ui.StayForm;

/**
 * User: mateusz
 * Date: 20.05.12
 */
public class StayListBinding extends AbstractGlazedListsBinding {
    public StayListBinding(FormModel formModel, String formPropertyPath) {
        super(formModel, formPropertyPath);
        setDialogId("stayListBindingDialog");
        setAddSupported(true);
        setEditSupported(true);
        setRemoveSupported(true);
        setShowDetailSupported(true);
    }

    protected TableDescription getTableDescription() {
        PropertyColumnTableDescription desc = new PropertyColumnTableDescription("stayListBinding", Stay.class);
        desc.addPropertyColumn("department.name");
        desc.addPropertyColumn("service");
        desc.addPropertyColumn("incomeDate");
        desc.addPropertyColumn("outcomeDate");
        return desc;
    }

    // detail form behavior

    @Override
    protected Object getNewFormObject() {
        return new Stay();
    }

    @Override
    protected AbstractForm createAddEditForm() {
        return new StayForm();
    }

    @Override
    protected AbstractForm createDetailForm() {
        AbstractForm f = new StayForm();
        f.getFormModel().setReadOnly(true);
        return f;
    }
}
