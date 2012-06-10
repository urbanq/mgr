package pl.edu.agh.ui;

import org.valkyriercp.widget.editor.provider.AbstractDataProvider;
import pl.edu.agh.domain.JGPFilter;
import pl.edu.agh.service.JGPService;

import java.util.List;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class JGPDataProvider extends AbstractDataProvider {
    private JGPService service;

    public JGPDataProvider(JGPService service) {
        super("jgpDataProvider");
        this.service = service;
    }

    public List getList(Object criteria) {
        if (criteria instanceof JGPFilter) {
            JGPFilter filter = (JGPFilter) criteria;
            return service.findJGP(filter);
        } else {
            throw new IllegalArgumentException("This provider can only filter through JGPFilter, not " + criteria.getClass());
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
