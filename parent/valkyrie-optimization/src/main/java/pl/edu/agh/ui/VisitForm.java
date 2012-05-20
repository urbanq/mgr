package pl.edu.agh.ui;

import com.jgoodies.forms.layout.FormLayout;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.builder.FormLayoutFormBuilder;
import org.valkyriercp.widget.AbstractFocussableWidgetForm;
import pl.edu.agh.domain.Visit;

import javax.swing.*;

/**
 * User: mateusz
 * Date: 20.05.12
 */
public class VisitForm extends AbstractFocussableWidgetForm
{
    @Override
    public FormModel createFormModel() {
        return formModelFactory.createFormModel(new Visit(), "visitForm");
    }

    protected JComponent createFormControl()
    {
        FormLayout layout = new FormLayout("right:pref, 4dlu, default", "default");
        FormLayoutFormBuilder builder = new FormLayoutFormBuilder(getBindingFactory(), layout);

        builder.addPropertyAndLabel("department");
        builder.nextRow();
        builder.addPropertyAndLabel("service");
        builder.nextRow();
        builder.addPropertyAndLabel("incomeDate", "jxDatePickerDateFieldBinder");
        builder.nextRow();
        builder.addPropertyAndLabel("outcomeDate", "jxDatePickerDateFieldBinder");

        return builder.getPanel();
    }
}