package pl.edu.agh.ui;

import com.jgoodies.forms.layout.FormLayout;
import org.valkyriercp.form.FilterForm;
import org.valkyriercp.form.builder.FormLayoutFormBuilder;
import pl.edu.agh.domain.ICD9;
import pl.edu.agh.domain.ICD9Filter;

import javax.swing.*;

/**
 * User: mateusz
 * Date: 12.05.12
 */
public class ICD9FilterForm extends FilterForm {
    public ICD9FilterForm() {
        super("icd9FilterForm");
    }

    @Override
    protected Object newFormObject() {
        return new ICD9Filter();
    }

    @Override
    public void setFormObject(Object formObject) {
        if(formObject instanceof ICD9) {
            super.setFormObject(ICD9Filter.fromICD9((ICD9) formObject));
        } else {
            super.setFormObject(formObject);
        }
    }

    protected JComponent createFormControl() {
        FormLayout layout = new FormLayout("default, 3dlu, fill:pref:nogrow", "default");
        FormLayoutFormBuilder builder = new FormLayoutFormBuilder(getBindingFactory(), layout);
        builder.addPropertyAndLabel("code");
        builder.nextRow();
        builder.addPropertyAndLabel("name");
        return builder.getPanel();
    }
}
