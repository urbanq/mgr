package pl.edu.agh.ui.binder;

import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.AbstractForm;
import org.valkyriercp.form.binding.swing.AbstractGlazedListsBinding;
import org.valkyriercp.widget.table.PropertyColumnTableDescription;
import org.valkyriercp.widget.table.TableDescription;
import pl.edu.agh.domain.Visit;
import pl.edu.agh.ui.VisitForm;

/**
 * User: mateusz
 * Date: 20.05.12
 */
public class VisitListBinding extends AbstractGlazedListsBinding {
    public VisitListBinding(FormModel formModel, String formPropertyPath) {
        super(formModel, formPropertyPath);
        setDialogId("visitListBindingDialog");
        setAddSupported(true);
        setEditSupported(true);
        setRemoveSupported(true);
        setShowDetailSupported(true);
    }

    protected TableDescription getTableDescription() {
        PropertyColumnTableDescription desc = new PropertyColumnTableDescription("visitListBinding", Visit.class);
        desc.addPropertyColumn("department.name");
        desc.addPropertyColumn("service");
        desc.addPropertyColumn("incomeDate");
        desc.addPropertyColumn("outcomeDate");
        return desc;
    }

    // detail form behavior

    @Override
    protected Object getNewFormObject() {
        return new Visit();
    }

    @Override
    protected AbstractForm createAddEditForm() {
        return new VisitForm();
    }

    @Override
    protected AbstractForm createDetailForm() {
        AbstractForm f = new VisitForm();
        f.getFormModel().setReadOnly(true);
        return f;
    }
}
