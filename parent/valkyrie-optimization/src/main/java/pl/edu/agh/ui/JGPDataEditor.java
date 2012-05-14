package pl.edu.agh.ui;

import org.valkyriercp.widget.editor.DefaultDataEditorWidget;
import org.valkyriercp.widget.table.PropertyColumnTableDescription;
import pl.edu.agh.domain.JGP;

import javax.annotation.PostConstruct;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class JGPDataEditor extends DefaultDataEditorWidget {
    private JGPDataProvider jgpDataProvider;

    public JGPDataEditor(JGPDataProvider jgpDataProvider) {
        super("jgpDataEditor");
        this.jgpDataProvider = jgpDataProvider;
    }

    @PostConstruct
    public void postConstruct() {
        setDataProvider(jgpDataProvider);
        setDetailForm(new JGPForm());
        setFilterForm(new JGPFilterForm());

        PropertyColumnTableDescription tableDescription = new PropertyColumnTableDescription("jgpDataEditor", JGP.class);
        tableDescription.addPropertyColumn("code").withFixedWidth(50);
        tableDescription.addPropertyColumn("productCode").withFixedWidth(120);
        tableDescription.addPropertyColumn("name");
        setTableWidget(tableDescription);
    }
}
