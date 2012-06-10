package pl.edu.agh.ui;

import org.valkyriercp.widget.editor.provider.AbstractDataProvider;
import pl.edu.agh.domain.ICD10;
import pl.edu.agh.domain.ICD10Filter;
import pl.edu.agh.service.ICD10Service;

import java.util.List;

public class ICD10DataProvider extends AbstractDataProvider {
    private ICD10Service service;

    public ICD10DataProvider(ICD10Service service) {
        super("icd10DataProvider");
        this.service = service;
    }

    public List getList(Object criteria) {
        if (criteria instanceof ICD10Filter) {
            ICD10Filter filter = (ICD10Filter) criteria;
            return service.findICD10(filter);
        } else if(criteria instanceof ICD10) {
            return service.findICD10(ICD10Filter.fromICD10((ICD10) criteria));
        } else {
            throw new IllegalArgumentException("This provider can only filter through ICD10Filter, not " + criteria.getClass());
        }
    }

    public boolean supportsFiltering() {
        return true;
    }

    public boolean supportsUpdate() {
        return false;
    }

    public boolean supportsCreate() {
        return false;
    }

    public boolean supportsClone() {
        return false;
    }

    public boolean supportsDelete() {
        return false;
    }
}
