package pl.edu.agh.ui;

import com.jgoodies.forms.layout.FormLayout;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.builder.FormLayoutFormBuilder;
import org.valkyriercp.widget.TabbedForm;
import pl.edu.agh.domain.GrouperFormObject;

/**
 * User: mateusz
 * Date: 16.05.12
 */
public class GrouperForm extends TabbedForm {
    @Override
    protected Tab[] getTabs() {
        FormLayout layout = new FormLayout("default, 3dlu, fill:pref:nogrow, 3dlu, 100dlu", "default");
        FormLayoutFormBuilder builder = new FormLayoutFormBuilder(getBindingFactory(), layout);
        setFocusControl(builder.addPropertyAndLabel("department")[1]);
//        builder.nextRow();
//        builder.addPropertyAndLabel("lastname");
//        builder.nextRow();
//        builder.addPropertyAndLabel("pesel");

        return new Tab[] { new Tab("baseTab", builder.getPanel())};
    }

    @Override
    public FormModel createFormModel() {
        return formModelFactory.createFormModel(new GrouperFormObject(), "grouperForm");
    }
}
