package pl.edu.agh.ui.binder;

import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.AbstractForm;
import org.valkyriercp.form.binding.swing.AbstractGlazedListsBinding;
import org.valkyriercp.widget.table.PropertyColumnTableDescription;
import org.valkyriercp.widget.table.TableDescription;
import pl.edu.agh.domain.ICD9Wrapper;
import pl.edu.agh.ui.ICD9WrapperForm;

/**
 * User: mateusz
 * Date: 20.05.12
 */
public class ICD9ListBinding extends AbstractGlazedListsBinding {
    public ICD9ListBinding(FormModel formModel, String formPropertyPath) {
        super(formModel, formPropertyPath);
        setDialogId("icd9ListBindingDialog");
        setAddSupported(true);
        setEditSupported(true);
        setRemoveSupported(true);
        setShowDetailSupported(true);
    }

    protected TableDescription getTableDescription() {
        PropertyColumnTableDescription desc = new PropertyColumnTableDescription("icd9ListBinding", ICD9Wrapper.class);
        desc.addPropertyColumn("procedureDate");
        desc.addPropertyColumn("icd9.code");
        desc.addPropertyColumn("icd9.name");
        return desc;
    }

    // detail form behavior

    @Override
    protected Object getNewFormObject() {
        return new ICD9Wrapper();
    }

    @Override
    protected AbstractForm createAddEditForm() {
        return new ICD9WrapperForm();
    }

    @Override
    protected AbstractForm createDetailForm() {
        AbstractForm f = new ICD9WrapperForm();
        f.getFormModel().setReadOnly(true);
        return f;
    }
}
