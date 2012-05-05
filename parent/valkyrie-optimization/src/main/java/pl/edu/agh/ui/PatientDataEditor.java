package pl.edu.agh.ui;

import org.valkyriercp.widget.editor.DefaultDataEditorWidget;
import org.valkyriercp.widget.table.PropertyColumnTableDescription;
import pl.edu.agh.domain.Patient;

import javax.annotation.PostConstruct;


public class PatientDataEditor extends DefaultDataEditorWidget {
    private PatientDataProvider patientDataProvider;

    public PatientDataEditor(PatientDataProvider patientDataProvider)
    {
        super("patientDataEditor");
        this.patientDataProvider = patientDataProvider;
    }

    @PostConstruct
    public void postConstruct() {
        setDataProvider(patientDataProvider);
        setDetailForm(new PatientForm());
        setFilterForm(new PatientFilterForm());

        PropertyColumnTableDescription tableDescription = new PropertyColumnTableDescription("patientDataEditor", Patient.class);
        tableDescription.addPropertyColumn("firstName");
        tableDescription.addPropertyColumn("lastName");
        tableDescription.addPropertyColumn("pesel");
        setTableWidget(tableDescription);
    }
}
