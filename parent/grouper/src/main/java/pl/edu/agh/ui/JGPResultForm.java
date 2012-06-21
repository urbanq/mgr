package pl.edu.agh.ui;

import com.jgoodies.forms.layout.FormLayout;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.builder.FormLayoutFormBuilder;
import org.valkyriercp.widget.AbstractFocussableWidgetForm;
import pl.edu.agh.domain.JGPResult;

import javax.swing.*;

/**
 * @author mateusz
 * @date 21.06.12
 */
public class JGPResultForm extends AbstractFocussableWidgetForm {
    @Override
    protected JComponent createFormControl() {
        FormLayout layout = new FormLayout("right:pref, 4dlu, default", "default");
        FormLayoutFormBuilder builder = new FormLayoutFormBuilder(getBindingFactory(), layout);

        builder.addPropertyAndLabel("jgp.code");
        builder.nextRow();
        builder.addPropertyAndLabel("jgp.name");
        builder.nextRow();
        builder.addPropertyAndLabel("jgp.productCode");
        builder.nextRow();
        builder.addPropertyAndLabel("value");
        builder.nextRow();
        builder.addPropertyAndLabel("stay.incomeDate");
        builder.nextRow();
        builder.addPropertyAndLabel("stay.outcomeDate");
        builder.nextRow();
        builder.addPropertyAndLabel("stay.service");
        builder.nextRow();
        builder.addPropertyAndLabel("stay.department.name");
        builder.nextRow();
        builder.addHorizontalSeparator("jgpResultForm.reasonsSeparator", 3);
        builder.nextRow("fill:default:grow");
//        ICD10ListBinding icd10ListBinding = new ICD10ListBinding(getFormModel(), "recognitions");
//        builder.addBinding(icd10ListBinding, 1, builder.getRow(), 3, 1);

        return builder.getPanel();
    }

    @Override
    public FormModel createFormModel() {
        return formModelFactory.createFormModel(new JGPResult(),"jgpResultForm");
    }
}
