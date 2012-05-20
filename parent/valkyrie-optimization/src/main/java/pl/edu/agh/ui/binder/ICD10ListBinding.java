package pl.edu.agh.ui.binder;

import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.AbstractForm;
import org.valkyriercp.form.binding.swing.AbstractGlazedListsBinding;
import org.valkyriercp.widget.table.PropertyColumnTableDescription;
import org.valkyriercp.widget.table.TableDescription;
import pl.edu.agh.domain.ICD10Wrapper;
import pl.edu.agh.ui.ICD10WrapperForm;

/**
 * User: mateusz
 * Date: 20.05.12
 */
public class ICD10ListBinding extends AbstractGlazedListsBinding {
    public ICD10ListBinding(FormModel formModel, String formPropertyPath) {
        super(formModel, formPropertyPath);
        setDialogId("icd10ListBindingDialog");
        setAddSupported(true);
        setEditSupported(true);
        setRemoveSupported(true);
        setShowDetailSupported(true);
    }

    protected TableDescription getTableDescription() {
        PropertyColumnTableDescription desc = new PropertyColumnTableDescription("icd10ListBinding", ICD10Wrapper.class);
        desc.addPropertyColumn("icd10.code");
        desc.addPropertyColumn("icd10.name");
        return desc;
    }

    // detail form behavior

    @Override
    protected Object getNewFormObject() {
        return new ICD10Wrapper();
    }

    @Override
    protected AbstractForm createAddEditForm() {
        return new ICD10WrapperForm();
    }

    @Override
    protected AbstractForm createDetailForm() {
        AbstractForm f = new ICD10WrapperForm();
        f.getFormModel().setReadOnly(true);
        return f;
    }
}
