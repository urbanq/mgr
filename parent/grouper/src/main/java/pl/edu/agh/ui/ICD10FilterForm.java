package pl.edu.agh.ui;

import com.jgoodies.forms.layout.FormLayout;
import org.valkyriercp.form.FilterForm;
import org.valkyriercp.form.builder.FormLayoutFormBuilder;
import pl.edu.agh.domain.ICD10;
import pl.edu.agh.domain.ICD10Filter;

import javax.swing.*;


public class ICD10FilterForm extends FilterForm {
    public ICD10FilterForm() {
        super("icd10FilterForm");
    }

    @Override
    protected Object newFormObject() {
        return new ICD10Filter();
    }

    @Override
    public void setFormObject(Object formObject)
    {
        if(formObject instanceof ICD10)
        {
            super.setFormObject(ICD10Filter.fromICD10((ICD10) formObject));
        }
        else
        {
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
