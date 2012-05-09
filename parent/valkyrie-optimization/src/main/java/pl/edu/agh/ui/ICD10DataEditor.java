package pl.edu.agh.ui;


import org.valkyriercp.widget.editor.DefaultDataEditorWidget;
import org.valkyriercp.widget.table.PropertyColumnTableDescription;
import pl.edu.agh.domain.ICD10;

import javax.annotation.PostConstruct;

public class ICD10DataEditor extends DefaultDataEditorWidget {
    private ICD10DataProvider icd10DataProvider;

    public ICD10DataEditor(ICD10DataProvider icd10DataProvider) {
        super("icd10DataEditor");
        this.icd10DataProvider = icd10DataProvider;
    }

    @PostConstruct
    public void postConstruct() {
        setDataProvider(icd10DataProvider);
        setDetailForm(new ICD10Form());
        setFilterForm(new ICD10FilterForm());

        PropertyColumnTableDescription tableDescription = new PropertyColumnTableDescription("icd10DataEditor", ICD10.class);
        tableDescription.addPropertyColumn("code");
        tableDescription.addPropertyColumn("name");
        setTableWidget(tableDescription);
    }
}
