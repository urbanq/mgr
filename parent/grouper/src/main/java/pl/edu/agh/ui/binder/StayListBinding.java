package pl.edu.agh.ui.binder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.valkyriercp.binding.form.FormModel;
import org.valkyriercp.form.AbstractForm;
import org.valkyriercp.form.binding.swing.AbstractGlazedListsBinding;
import org.valkyriercp.widget.table.PropertyColumnTableDescription;
import org.valkyriercp.widget.table.TableDescription;
import pl.edu.agh.dao.DepartmentDao;
import pl.edu.agh.domain.Episode;
import pl.edu.agh.domain.Stay;
import pl.edu.agh.ui.StayForm;

import java.util.Date;

/**
 * User: mateusz
 * Date: 20.05.12
 */
public class StayListBinding extends AbstractGlazedListsBinding {
    @Autowired
    private DepartmentDao departmentDao;

    public StayListBinding(FormModel formModel, String formPropertyPath) {
        super(formModel, formPropertyPath);
        setDialogId("stayListBindingDialog");
        setAddSupported(true);
        setEditSupported(true);
        setRemoveSupported(true);
        setShowDetailSupported(true);
    }

    protected TableDescription getTableDescription() {
        PropertyColumnTableDescription desc = new PropertyColumnTableDescription("stayListBinding", Stay.class);
        desc.addPropertyColumn("department.name");
        desc.addPropertyColumn("service");
        desc.addPropertyColumn("incomeDate");
        desc.addPropertyColumn("outcomeDate");
        return desc;
    }

    // detail form behavior

    @Override
    protected Object getNewFormObject() {
        Stay stay = new Stay();
        stay.setDepartment(departmentDao.get("111"));
        Episode episode = (Episode) getFormModel().getFormObject();
        stay.setEpisode(episode);
        if (CollectionUtils.isEmpty(episode.getStays())) {
            stay.setIncomeDate((Date) getFormModel().getValueModel("incomeDate").getValue());
            stay.setOutcomeDate((Date) getFormModel().getValueModel("outcomeDate").getValue());
        }
        return stay;
    }

    @Override
    protected AbstractForm createAddEditForm() {
        return new StayForm();
    }

    @Override
    protected AbstractForm createDetailForm() {
        AbstractForm f = new StayForm();
        f.getFormModel().setReadOnly(true);
        return f;
    }
}
