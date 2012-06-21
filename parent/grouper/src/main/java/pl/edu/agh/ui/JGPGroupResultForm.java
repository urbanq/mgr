package pl.edu.agh.ui;

import com.jgoodies.forms.layout.FormLayout;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.builder.FormLayoutFormBuilder;
import org.valkyriercp.widget.TabbedForm;
import pl.edu.agh.domain.JGPGroupResult;
import pl.edu.agh.ui.binder.JGPResultListBinding;

/**
 * @author mateusz
 * @date 14.06.12
 */
public class JGPGroupResultForm extends TabbedForm {
    private JGPGroupResult result;

    public JGPGroupResultForm(JGPGroupResult result) {
        this.result = result;
    }

    @Override
    protected Tab[] getTabs() {
        FormLayout layout = new FormLayout("fill:pref:grow", "fill:pref:grow");
        FormLayoutFormBuilder acceptedBuilder = new FormLayoutFormBuilder(getBindingFactory(), layout);
        JGPResultListBinding jgpResultListBinding = new JGPResultListBinding(getFormModel(), "accepted");
        acceptedBuilder.addBinding(jgpResultListBinding, 1, acceptedBuilder.getRow(), 1, 1);

        FormLayoutFormBuilder cheapBuilder = new FormLayoutFormBuilder(getBindingFactory(), layout);
        jgpResultListBinding = new JGPResultListBinding(getFormModel(), "cheap");
        cheapBuilder.addBinding(jgpResultListBinding, 1, acceptedBuilder.getRow(), 1, 1);

        FormLayoutFormBuilder expensiveBuilder = new FormLayoutFormBuilder(getBindingFactory(), layout);
        jgpResultListBinding = new JGPResultListBinding(getFormModel(), "expensive");
        expensiveBuilder.addBinding(jgpResultListBinding, 1, acceptedBuilder.getRow(), 1, 1);

        return new Tab[] { new Tab("accepted", acceptedBuilder.getPanel()),
                           new Tab("expensive", expensiveBuilder.getPanel()),
                           new Tab("cheap", cheapBuilder.getPanel())};
    }

    @Override
    public FormModel createFormModel() {
        return formModelFactory.createFormModel(result, "jgpGroupResultForm");
    }
}
