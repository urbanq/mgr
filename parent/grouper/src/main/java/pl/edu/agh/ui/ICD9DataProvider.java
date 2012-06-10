package pl.edu.agh.ui;

import org.valkyriercp.widget.editor.provider.AbstractDataProvider;
import pl.edu.agh.domain.ICD9;
import pl.edu.agh.domain.ICD9Filter;
import pl.edu.agh.service.ICD9Service;

import java.util.List;

/**
 * User: mateusz
 * Date: 12.05.12
 */
public class ICD9DataProvider extends AbstractDataProvider {
    private ICD9Service service;

    public ICD9DataProvider(ICD9Service service) {
        super("icd9DataProvider");
        this.service = service;
    }

    public List getList(Object criteria) {
        if (criteria instanceof ICD9Filter) {
            ICD9Filter filter = (ICD9Filter) criteria;
            return service.findICD9(filter);
        } else if(criteria instanceof ICD9) {
            return service.findICD9(ICD9Filter.fromICD9((ICD9) criteria));
        } else {
            throw new IllegalArgumentException("This provider can only filter through ICD9Filter, not " + criteria.getClass());
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
