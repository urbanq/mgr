package pl.edu.agh.ui;

import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.widget.TabbedForm;
import pl.edu.agh.domain.ICD9;

/**
 * User: mateusz
 * Date: 12.05.12
 */
public class ICD9Form extends TabbedForm {
    @Override
    protected Tab[] getTabs() {
        return new Tab[]{};
    }

    @Override
    public FormModel createFormModel() {
        return formModelFactory.createFormModel(new ICD9(),"icd9Form");
    }
}
