package pl.edu.agh.ui;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.valkyriercp.application.support.DefaultButtonFocusListener;
import org.valkyriercp.binding.validation.support.DefaultValidationResultsModel;
import org.valkyriercp.command.support.ActionCommand;
import org.valkyriercp.core.Messagable;
import org.valkyriercp.form.*;
import org.valkyriercp.util.ObjectUtils;
import org.valkyriercp.widget.AbstractTitledWidget;
import org.valkyriercp.widget.editor.AbstractDataEditorWidget;
import pl.edu.agh.domain.Episode;
import pl.edu.agh.domain.JGPGroupResult;
import pl.edu.agh.domain.Service;
import pl.edu.agh.service.JGPService;

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


    /**
     * service
     */
    @Autowired
    private JGPService service;

    public GrouperWidget() {
        setId("grouperWidget");
    }

    @PostConstruct
    public void postConstruct() {
        setDetailForm(new EpisodeForm());
        for (Service service : Service.values()) {
            final String messageKey = Service.class.getName() + "." + service.name();
            service.setMessage(applicationConfig.messageResolver().getMessage(messageKey));
        }
    }

    @Override
    public JComponent createWidgetContent() {
        return getDetailPanel();
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
                doGrouping();
            }
        };
        command.setSecurityControllerId(getId() + "." + GROUP_COMMAND_ID);
        applicationConfig.commandConfigurer().configure(command);
        getDetailForm().addGuarded(command, FormGuard.FORMERROR_GUARDED);
        return command;
    }

    protected void doGrouping() {
        getDetailForm().commit();
        JGPGroupResult result = null;
        try {
            result = executeGrouper((Episode) getDetailForm().getFormObject());
            result.execute();
            new JGPGroupResultDialog(result) {
                @Override
                protected boolean onFinish() {
                    groupCommand.setEnabled(true);
                    return true;
                }
            }.showDialog();
        } catch (RuntimeException e) {
            Object changedFormObject = getDetailForm().getFormObject();
            ObjectUtils.mapObjectOnFormModel(getDetailForm().getFormModel(), changedFormObject);
            throw e;
        }
    }

    private JGPGroupResult executeGrouper(Episode episode) {
        return service.group(episode);
    }

//    private Object executeBlockingJobInBackground(String description, Job job) {
//        ProgressMonitor progressMonitor = getStatusBar().getProgressMonitor();
//        BusyIndicator.showAt(getWindowControl());
//        progressMonitor.taskStarted(description, StatusBar.UNKNOWN);
//
//        Object result = Worker.post(job);
//
//        BusyIndicator.clearAt(getWindowControl());
//        progressMonitor.done();
//
//        return result;
//    }


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
