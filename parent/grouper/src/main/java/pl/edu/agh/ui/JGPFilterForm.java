package pl.edu.agh.ui;

import com.jgoodies.forms.layout.FormLayout;
import org.valkyriercp.form.FilterForm;
import org.valkyriercp.form.builder.FormLayoutFormBuilder;
import pl.edu.agh.domain.JGPFilter;

import javax.swing.*;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class JGPFilterForm extends FilterForm {
    public JGPFilterForm() {
        super("jgpFilterForm");
    }

    @Override
    protected Object newFormObject() {
        return new JGPFilter();
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
