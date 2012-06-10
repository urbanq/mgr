package pl.edu.agh.ui;

import com.jgoodies.forms.layout.FormLayout;
import org.valkyriercp.form.FilterForm;
import org.valkyriercp.form.builder.FormLayoutFormBuilder;
import pl.edu.agh.domain.PatientFilter;

import javax.swing.*;

public class PatientFilterForm extends FilterForm {
    public PatientFilterForm()
    {
        super("patientFilterForm");
    }

    @Override
    protected Object newFormObject()
    {
        return new PatientFilter();
    }

    protected JComponent createFormControl()
    {
        FormLayout layout = new FormLayout("default, 3dlu, fill:pref:nogrow", "default");
        FormLayoutFormBuilder builder = new FormLayoutFormBuilder(getBindingFactory(), layout);
        builder.addHorizontalSeparator("Quick search", 3);
        builder.nextRow();
        builder.addPropertyAndLabel("quickSearch");
        builder.nextRow();
        builder.addHorizontalSeparator("Firstname", 3);
        builder.nextRow();
        builder.addPropertyAndLabel("firstname");
        builder.nextRow();
        builder.addHorizontalSeparator("Lastname", 3);
        builder.nextRow();
        builder.addPropertyAndLabel("lastname");
        builder.nextRow();
        builder.addHorizontalSeparator("PESEL", 3);
        builder.nextRow();
        builder.addPropertyAndLabel("pesel");
        return builder.getPanel();
    }
}
