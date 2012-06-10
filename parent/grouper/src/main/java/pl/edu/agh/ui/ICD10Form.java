package pl.edu.agh.ui;

import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.widget.TabbedForm;
import pl.edu.agh.domain.ICD10;

public class ICD10Form extends TabbedForm {
    @Override
    protected Tab[] getTabs() {
//        FormLayout layout = new FormLayout("default, 3dlu, fill:pref:nogrow, 3dlu, 100dlu", "default");
//        FormLayoutFormBuilder builder = new FormLayoutFormBuilder(getBindingFactory(), layout);
//        setFocusControl(builder.addPropertyAndLabel("firstname")[1]);
//        builder.nextRow();
//        builder.addPropertyAndLabel("lastname");
//        builder.nextRow();
//        builder.addPropertyAndLabel("pesel");

        return new Tab[]{};
    }

    @Override
    public FormModel createFormModel() {
        return formModelFactory.createFormModel(new ICD10(),"icd10Form");
    }
}