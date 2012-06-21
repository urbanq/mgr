package pl.edu.agh.ui;

import org.valkyriercp.dialog.CloseAction;
import org.valkyriercp.dialog.ConfirmationDialog;
import org.valkyriercp.dialog.FormBackedDialogPage;
import org.valkyriercp.dialog.TitledPageApplicationDialog;
import org.valkyriercp.form.Form;
import pl.edu.agh.domain.JGPGroupResult;

import java.awt.*;

/**
 * @author mateusz
 * @date 14.06.12
 */
public class JGPGroupResultDialog extends TitledPageApplicationDialog {
    private Form form;

    public JGPGroupResultDialog(JGPGroupResult result) {
        setCloseAction(CloseAction.DISPOSE);
        form = new JGPGroupResultForm(result);
        setDialogPage(new FormBackedDialogPage(form));
        setPreferredSize(new Dimension(800, 600));
    }

    protected void onAboutToShow() {
        setTitle(getApplicationConfig().messageResolver().getMessage("jgpGroupResultDialog.title"));
    }

    protected boolean onFinish() {
        return true;
    }

    protected void onCancel() {
        // Warn the user if they are about to discard their changes
        if (form.getFormModel().isDirty()) {
            String msg = getApplicationConfig().messageResolver().getMessage("jgpGroupResultDialog.dirtyCancel.message");
            String title = getApplicationConfig().messageResolver().getMessage("jgpGroupResultDialog.dirtyCancel.title");
            ConfirmationDialog dlg = new ConfirmationDialog(title, msg) {
                protected void onConfirm() {
                    JGPGroupResultDialog.super.onCancel();
                }
            };
            dlg.showDialog();
        }
        else {
            super.onCancel();
        }
    }
}
