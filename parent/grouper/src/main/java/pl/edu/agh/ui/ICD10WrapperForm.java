package pl.edu.agh.ui;

import com.jgoodies.forms.layout.FormLayout;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.builder.FormLayoutFormBuilder;
import org.valkyriercp.widget.AbstractFocussableWidgetForm;
import pl.edu.agh.domain.ICD10Wrapper;

import javax.swing.*;

/**
 * User: mateusz
 * Date: 20.05.12
 */
public class ICD10WrapperForm extends AbstractFocussableWidgetForm {
    @Override
    public FormModel createFormModel() {
        return formModelFactory.createFormModel(new ICD10Wrapper(), "icd10WrapperForm");
    }

    protected JComponent createFormControl() {
        FormLayout layout = new FormLayout("default, 3dlu, 200dlu, 3dlu, 100dlu", "default");
        FormLayoutFormBuilder builder = new FormLayoutFormBuilder(getBindingFactory(), layout);

        setFocusControl(builder.addPropertyAndLabel("icd10", "icd10Binder")[1]);
        builder.nextRow();

        return builder.getPanel();
    }
}
