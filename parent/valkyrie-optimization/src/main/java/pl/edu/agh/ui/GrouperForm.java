package pl.edu.agh.ui;

import com.jgoodies.forms.layout.FormLayout;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.builder.FormLayoutFormBuilder;
import org.valkyriercp.widget.AbstractFocussableWidgetForm;
import pl.edu.agh.domain.Grouper;
import pl.edu.agh.ui.binder.VisitListBinding;

import javax.swing.*;

/**
 * User: mateusz
 * Date: 16.05.12
 */
public class GrouperForm extends AbstractFocussableWidgetForm {
    @Override
    protected JComponent createFormControl() {
        FormLayout layout = new FormLayout("right:pref, 4dlu, fill:pref:grow, 6dlu, right:pref, 4dlu, fill:pref:grow", "default");
        FormLayoutFormBuilder builder = new FormLayoutFormBuilder(getBindingFactory(), layout);
        setFocusControl(builder.addPropertyAndLabel("dateOfBirth", "jxDatePickerDateFieldBinder")[1]);
        builder.addPropertyAndLabel("sex", 5);
        builder.nextRow();
        builder.addPropertyAndLabel("incomeDate", "jxDatePickerDateFieldBinder");
        builder.addPropertyAndLabel("incomeModeLimit", 5);
        builder.nextRow();
        builder.addPropertyAndLabel("outcomeDate", "jxDatePickerDateFieldBinder");
        builder.addPropertyAndLabel("outcomeModeLimit", 5);
        builder.nextRow();
        builder.addPropertyAndLabel("hospitalType");
        builder.nextRow();
        builder.addHorizontalSeparator("grouperForm.visitSeparator", 7);
        builder.nextRow("fill:default:grow");
        VisitListBinding visitListBinding = new VisitListBinding(getFormModel(), "visits");
        builder.addBinding(visitListBinding, 1, builder.getRow(), 7, 1);

        return builder.getPanel();
    }

    @Override
    public FormModel createFormModel() {
        return formModelFactory.createFormModel(new Grouper(), "grouperForm");
    }
}
