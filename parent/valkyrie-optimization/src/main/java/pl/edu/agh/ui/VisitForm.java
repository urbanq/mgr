package pl.edu.agh.ui;

import com.jgoodies.forms.layout.FormLayout;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.builder.FormLayoutFormBuilder;
import org.valkyriercp.widget.AbstractFocussableWidgetForm;
import pl.edu.agh.domain.Visit;
import pl.edu.agh.ui.binder.ICD10ListBinding;
import pl.edu.agh.ui.binder.ICD9ListBinding;

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
        builder.nextRow();
        builder.nextRow();
        builder.addHorizontalSeparator("visitForm.recognitionsSeparator", 3);
        builder.nextRow("fill:default:grow");
        ICD10ListBinding icd10ListBinding = new ICD10ListBinding(getFormModel(), "recognitions");
        builder.addBinding(icd10ListBinding, 1, builder.getRow(), 3, 1);

        builder.nextRow();
        builder.addHorizontalSeparator("visitForm.proceduresSeparator", 3);
        builder.nextRow("fill:default:grow");
        ICD9ListBinding icd9ListBinding = new ICD9ListBinding(getFormModel(), "procedures");
        builder.addBinding(icd9ListBinding, 1, builder.getRow(), 3, 1);

        return builder.getPanel();
    }
}