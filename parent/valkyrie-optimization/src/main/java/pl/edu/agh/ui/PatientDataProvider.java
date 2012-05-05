package pl.edu.agh.ui;

import org.valkyriercp.widget.editor.provider.AbstractDataProvider;
import pl.edu.agh.domain.Patient;
import pl.edu.agh.domain.PatientFilter;
import pl.edu.agh.domain.PatientService;

import java.util.List;


public class PatientDataProvider extends AbstractDataProvider {
    private PatientService service;

    public PatientDataProvider(PatientService service)
    {
        super("patientDataProvider");
        this.service = service;
    }

    public List getList(Object criteria)
    {
        if (criteria instanceof PatientFilter)
        {
            PatientFilter itemFilter = (PatientFilter) criteria;
            return service.findPatients(itemFilter);
        }
        else
        {
            throw new IllegalArgumentException("This provider can only filter through PatientFilter, not " + criteria.getClass());
        }
    }

    public boolean supportsFiltering() {
        return true;
    }

    public boolean supportsUpdate() {
        return true;
    }

    public boolean supportsCreate() {
        return true;
    }

    public boolean supportsClone() {
        return false;
    }

    public boolean supportsDelete() {
        return true;
    }

    @Override
    public Object doCreate(Object newData) {
       return service.create((Patient) newData);
    }

    @Override
    public Object doUpdate(Object updatedData) {
        return service.update((Patient) updatedData);
    }

    @Override
    public void doDelete(Object dataToRemove) {
        service.delete((Patient) dataToRemove);
    }
}
