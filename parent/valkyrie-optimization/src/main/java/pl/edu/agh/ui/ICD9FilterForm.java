package pl.edu.agh.ui;

import com.jgoodies.forms.layout.FormLayout;
import org.valkyriercp.form.FilterForm;
import org.valkyriercp.form.builder.FormLayoutFormBuilder;
import pl.edu.agh.domain.ICD9Filter;

import javax.swing.*;

/**
 * User: mateusz
 * Date: 12.05.12
 */
public class ICD9FilterForm extends FilterForm {
    public ICD9FilterForm()
    {
        super("icd9FilterForm");
    }

    @Override
    protected Object newFormObject()
    {
        return new ICD9Filter();
    }

    protected JComponent createFormControl()
    {
        FormLayout layout = new FormLayout("default, 3dlu, fill:pref:nogrow", "default");
        FormLayoutFormBuilder builder = new FormLayoutFormBuilder(getBindingFactory(), layout);
        builder.addPropertyAndLabel("code");
        builder.nextRow();
        builder.addPropertyAndLabel("name");
        return builder.getPanel();
    }
}
