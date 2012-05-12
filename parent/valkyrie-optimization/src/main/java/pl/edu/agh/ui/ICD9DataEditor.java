package pl.edu.agh.ui;

import org.valkyriercp.widget.editor.DefaultDataEditorWidget;
import org.valkyriercp.widget.table.PropertyColumnTableDescription;
import pl.edu.agh.domain.ICD9;

import javax.annotation.PostConstruct;

/**
 * User: mateusz
 * Date: 12.05.12
 */
public class ICD9DataEditor extends DefaultDataEditorWidget {
    private ICD9DataProvider icd9DataProvider;

    public ICD9DataEditor(ICD9DataProvider icd9DataProvider) {
        super("icd9DataEditor");
        this.icd9DataProvider = icd9DataProvider;
    }

    @PostConstruct
    public void postConstruct() {
        setDataProvider(icd9DataProvider);
        setDetailForm(new ICD9Form());
        setFilterForm(new ICD9FilterForm());

        PropertyColumnTableDescription tableDescription = new PropertyColumnTableDescription("icd9DataEditor", ICD9.class);
        tableDescription.addPropertyColumn("code").withFixedWidth(90);
        tableDescription.addPropertyColumn("name");
        setTableWidget(tableDescription);
    }
}
