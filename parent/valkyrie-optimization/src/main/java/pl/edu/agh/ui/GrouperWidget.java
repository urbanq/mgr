package pl.edu.agh.ui;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import org.valkyriercp.application.support.DefaultButtonFocusListener;
import org.valkyriercp.binding.validation.support.DefaultValidationResultsModel;
import org.valkyriercp.command.support.ActionCommand;
import org.valkyriercp.core.Messagable;
import org.valkyriercp.form.*;
import org.valkyriercp.util.ObjectUtils;
import org.valkyriercp.widget.AbstractTitledWidget;
import org.valkyriercp.widget.editor.AbstractDataEditorWidget;
import pl.edu.agh.domain.Service;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * User: mateusz
 * Date: 16.05.12
 */
public class GrouperWidget extends AbstractTitledWidget {
    protected static final String GROUP_COMMAND_ID = "groupCommand";
    private final CellConstraints cc = new CellConstraints();
    private final DefaultValidationResultsModel validationResultsModel = new DefaultValidationResultsModel();
    private ActionCommand groupCommand;

    /**
     * Detailform
     */
    private AbstractForm detailForm;


    public GrouperWidget() {
        setId("grouperWidget");
    }

    @PostConstruct
    public void postConstruct() {
        setDetailForm(new GrouperForm());
        for (Service service : Service.values()) {
            final String messageKey = Service.class.getName() + "." + service.name();
            service.setMessage(applicationConfig.messageResolver().getMessage(messageKey));
        }
    }

    @Override
    public JComponent createWidgetContent() {
        return getDetailPanel();  //To change body of implemented methods use File | Settings | File Templates.
    }

    protected JComponent getDetailPanel()
    {
        ColumnSpec[] columnSpecs = new ColumnSpec[]{AbstractDataEditorWidget.FILL_COLUMN_SPEC};
        RowSpec[] rowSpecs = new RowSpec[]{FormFactory.LINE_GAP_ROWSPEC, // gap
                AbstractDataEditorWidget.FILL_ROW_SPEC, // detailpanel itself (form)
                FormFactory.LINE_GAP_ROWSPEC, // gap
                FormFactory.DEFAULT_ROWSPEC // buttons for detailpanel
        };
        JPanel detailPanel = new JPanel(new FormLayout(columnSpecs, rowSpecs));

        Form detailForm = getDetailForm();
        newSingleLineResultsReporter(this);
        detailPanel.add(detailForm.getControl(), cc.xy(1, 2));
        detailPanel.add(getButtonsPanel(), cc.xy(1, 4));

        return detailPanel;
    }

    protected JPanel getButtonsPanel()
    {
        ColumnSpec[] columnSpecs = new ColumnSpec[]{AbstractDataEditorWidget.FILL_NOGROW_COLUMN_SPEC, //commit
                FormFactory.RELATED_GAP_COLSPEC, // gap
                AbstractDataEditorWidget.FILL_NOGROW_COLUMN_SPEC, // clear
                FormFactory.RELATED_GAP_COLSPEC, // gap
                FormFactory.DEFAULT_COLSPEC, // separator
                FormFactory.RELATED_GAP_COLSPEC, // gap
                AbstractDataEditorWidget.FILL_NOGROW_COLUMN_SPEC, // quickadd
        };

        RowSpec[] rowSpecs = new RowSpec[]{AbstractDataEditorWidget.FILL_ROW_SPEC};
        FormLayout formLayout = new FormLayout(columnSpecs, rowSpecs);
        JPanel buttonPanel = new JPanel(formLayout);
        buttonPanel.add(getCommitComponent(), cc.xy(1, 1));
        buttonPanel.add(getClearCommand().createButton(), cc.xy(3, 1));
        return buttonPanel;
    }

    protected JComponent getCommitComponent()
    {
        DefaultButtonFocusListener.setDefaultButton(getDetailForm().getControl(), getGroupCommand());
        return getGroupCommand().createButton();
    }

    /**
     * Returns the create command, lazily creates one if needed.
     */
    public ActionCommand getGroupCommand()
    {
        if (groupCommand == null)
        {
            groupCommand = createGroupCommand();
        }
        return groupCommand;
    }

    /**
     * Returns the create command, lazily creates one if needed.
     */
    public ActionCommand getClearCommand()
    {
        return getDetailForm().getNewFormObjectCommand();
    }

    /**
     * Creates the create command.
     */
    protected ActionCommand createGroupCommand()
    {
        ActionCommand command = new ActionCommand(GROUP_COMMAND_ID)
        {

            @Override
            protected void doExecuteCommand()
            {
                doCreate();
            }
        };
        command.setSecurityControllerId(getId() + "." + GROUP_COMMAND_ID);
        applicationConfig.commandConfigurer().configure(command);
        getDetailForm().addGuarded(command, FormGuard.LIKE_COMMITCOMMAND);
        return command;
    }

    protected void doCreate()
    {
        getDetailForm().commit();
        Object newObject = null;
        try
        {
            newObject = createNewEntity(getDetailForm().getFormObject());
        }
        catch (RuntimeException e)
        {
            Object changedFormObject = getDetailForm().getFormObject();
//            newRow(null);
            ObjectUtils.mapObjectOnFormModel(getDetailForm().getFormModel(), changedFormObject);
            throw e;
            // make form dirty, show error in messagepane and throw exception to display error dialog
        }
    }

    private Object createNewEntity(Object formObject) {
        //TODO in future this method will implement gruper algorithm
        return null;  //To change body of created methods use File | Settings | File Templates.
    }


    public ValidationResultsReporter newSingleLineResultsReporter(Messagable messagable)
    {
        return new SimpleValidationResultsReporter(getValidationResults(), messagable);
    }

    protected final DefaultValidationResultsModel getValidationResults()
    {
        return validationResultsModel;
    }

    public AbstractForm getDetailForm() {
        return detailForm;
    }

    /**
     * Set the form that will handle one detail item.
     */
    protected void setDetailForm(AbstractForm detailForm)
    {
        if (this.detailForm != null)
        {
            validationResultsModel.remove(this.detailForm.getFormModel().getValidationResults());
        }

        this.detailForm = detailForm;

        if (this.detailForm != null)
        {
            validationResultsModel.add(this.detailForm.getFormModel().getValidationResults());
        }
    }
}
