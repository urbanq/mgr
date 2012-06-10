package pl.edu.agh.ui;

import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.widget.TabbedForm;
import pl.edu.agh.domain.JGP;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class JGPForm extends TabbedForm {
    @Override
    protected Tab[] getTabs() {
        return new Tab[]{};
    }

    @Override
    public FormModel createFormModel() {
        return formModelFactory.createFormModel(new JGP(),"jgpForm");
    }
}
