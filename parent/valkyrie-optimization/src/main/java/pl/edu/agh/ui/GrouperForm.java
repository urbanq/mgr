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
        //TODO form layout
//        FormLayout layout = new FormLayout("default, 3dlu, fill:pref:nogrow, 3dlu, 100dlu, 3dlu, 100dlu", "default");
        FormLayout layout = new FormLayout("right:pref, 4dlu, fill:pref:grow, 6dlu, right:pref, 4dlu, fill:pref:grow", "default");
        FormLayoutFormBuilder builder = new FormLayoutFormBuilder(getBindingFactory(), layout);
        setFocusControl(builder.addPropertyAndLabel("dateOfBirth", "jxDatePickerDateFieldBinder")[1]);
        builder.addPropertyAndLabel("sex", 5);
        builder.nextRow();
        builder.addPropertyAndLabel("income", "jxDatePickerDateFieldBinder");
        builder.nextRow();
        builder.addPropertyAndLabel("outcome", "jxDatePickerDateFieldBinder");

        return new Tab[] { new Tab("baseTab", builder.getPanel())};
    }

    @Override
    public FormModel createFormModel() {
        return formModelFactory.createFormModel(new GrouperFormObject(), "grouperForm");
    }
}
