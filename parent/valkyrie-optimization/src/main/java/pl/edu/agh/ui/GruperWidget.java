package pl.edu.agh.ui;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.*;
import org.valkyriercp.application.support.DefaultButtonFocusListener;
import org.valkyriercp.binding.validation.support.DefaultValidationResultsModel;
import org.valkyriercp.command.support.ActionCommand;
import org.valkyriercp.core.Messagable;
import org.valkyriercp.form.*;
import org.valkyriercp.util.ObjectUtils;
import org.valkyriercp.widget.AbstractTitledWidget;
import org.valkyriercp.widget.editor.AbstractDataEditorWidget;

import javax.annotation.PostConstruct;
import javax.swing.*;

/**
 * User: mateusz
 * Date: 16.05.12
 */
public class GruperWidget extends AbstractTitledWidget {
    protected static final String CREATE_COMMAND_ID = "create";
    private final CellConstraints cc = new CellConstraints();
    private final DefaultValidationResultsModel validationResultsModel = new DefaultValidationResultsModel();
    private ActionCommand createRowCommand;

    /**
     * Detailform
     */
    private AbstractForm detailForm;


    public GruperWidget() {
        setId("gruperWidget");
    }

    @PostConstruct
    public void postConstruct() {
        setDetailForm(new GruperForm());
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
        detailPanel.add(getDetailControlPanel(), cc.xy(1, 4));

        // force form readonly if adding & updating is not supported
//        if (!isAddRowSupported() && !isUpdateRowSupported())
//        {
//            detailForm.getFormModel().setReadOnly(true);
//        }

        return detailPanel;
    }

    private JComponent getDetailControlPanel()
    {
        ColumnSpec[] columnSpecs = new ColumnSpec[]{AbstractDataEditorWidget.FILL_NOGROW_COLUMN_SPEC, // edit buttons
                AbstractDataEditorWidget.FILL_COLUMN_SPEC, // glue
                AbstractDataEditorWidget.FILL_NOGROW_COLUMN_SPEC, // navigation buttons
                AbstractDataEditorWidget.FILL_COLUMN_SPEC, // glue
                new ColumnSpec(ColumnSpec.RIGHT, Sizes.DEFAULT, FormSpec.DEFAULT_GROW) // list summary
        };

        RowSpec[] rowSpecs = new RowSpec[]{AbstractDataEditorWidget.FILL_ROW_SPEC};
        FormLayout formLayout = new FormLayout(columnSpecs, rowSpecs);
        // coupled glue space around nav buttons!
        formLayout.setColumnGroups(new int[][]{{2, 4}});
        JPanel buttonPanel = new JPanel(formLayout);

        JComponent editButtons = getEditButtons();
//        JComponent tableButtonBar = getTableWidget().getButtonBar();
        if (editButtons != null)
        {
            buttonPanel.add(editButtons, cc.xy(1, 1));
        }
//        if (tableButtonBar != null)
//        {
//            buttonPanel.add(tableButtonBar, cc.xy(3, 1));
//        }

//        buttonPanel.add(getTableWidget().getListSummaryLabel(), cc.xy(5, 1));

        return buttonPanel;
    }

    protected JComponent getEditButtons()
    {
//        if (!isAddRowSupported() && !isUpdateRowSupported())
//        {
//            return null;
//        }

        ColumnSpec[] columnSpecs = new ColumnSpec[]{AbstractDataEditorWidget.FILL_NOGROW_COLUMN_SPEC, // save
                FormFactory.RELATED_GAP_COLSPEC, // gap
                AbstractDataEditorWidget.FILL_NOGROW_COLUMN_SPEC, // undo
                FormFactory.RELATED_GAP_COLSPEC, // gap
                FormFactory.DEFAULT_COLSPEC, // separator
                FormFactory.RELATED_GAP_COLSPEC, // gap
                AbstractDataEditorWidget.FILL_NOGROW_COLUMN_SPEC, // quickadd
        };

        RowSpec[] rowSpecs = new RowSpec[]{AbstractDataEditorWidget.FILL_ROW_SPEC};
        FormLayout formLayout = new FormLayout(columnSpecs, rowSpecs);
        JPanel buttonPanel = new JPanel(formLayout);
        buttonPanel.add(getCommitComponent(), cc.xy(1, 1));
//        buttonPanel.add(getRevertCommand().createButton(), cc.xy(3, 1));
//        if (isAddRowSupported())
//        {
//            buttonPanel.add(new JSeparator(SwingConstants.VERTICAL), cc.xy(5, 1));
//            buttonPanel.add(createQuickAddCheckBox(), cc.xy(7, 1));
//        }
        return buttonPanel;
    }

    protected JComponent getCommitComponent()
    {
        DefaultButtonFocusListener.setDefaultButton(getDetailForm().getControl(), getCreateCommand());
        return getCreateCommand().createButton();
//        if (isAddRowSupported() && isUpdateRowSupported())
//        {
//            saveUpdateSwitcher = new CardLayout();
//            saveUpdatePanel = new JPanel(saveUpdateSwitcher);
//            saveUpdatePanel.add(getCreateCommand().createButton(), CREATE_COMMAND_ID);
//            saveUpdatePanel.add(getUpdateCommand().createButton(), UPDATE_COMMAND_ID);
//            return saveUpdatePanel;
//        }
//        if (isAddRowSupported())
//        {
//            DefaultButtonFocusListener.setDefaultButton(getDetailForm().getControl(), getCreateCommand());
//            return getCreateCommand().createButton();
//        }
//
//        DefaultButtonFocusListener.setDefaultButton(getDetailForm().getControl(), getUpdateCommand());
//        return getUpdateCommand().createButton();
    }

    /**
     * Returns the create command, lazily creates one if needed.
     */
    public ActionCommand getCreateCommand()
    {
        if (createRowCommand == null)
        {
            createRowCommand = createCreateCommand();
        }
        return createRowCommand;
    }

    /**
     * Creates the create command.
     */
    protected ActionCommand createCreateCommand()
    {
        ActionCommand command = new ActionCommand(CREATE_COMMAND_ID)
        {

            @Override
            protected void doExecuteCommand()
            {
                doCreate();
            }
        };
        command.setSecurityControllerId(getId() + "." + CREATE_COMMAND_ID);
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


//            // select row only if user hasn't made another selection in
//            // table and this commit is triggered by the save-changes
//            // dialog and if not in quick add mode
//            if (newObject != null && !getTableWidget().hasSelection())
//            {
//                if (getTableWidget().selectRowObject(newObject, null) == -1)
//                {
//                    // select row wasn't succesfull, maybe search string
//                    // was filled in?
//                    setSearchString(null);
//                    getTableWidget().selectRowObject(newObject, null);
//                }
//            }
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
