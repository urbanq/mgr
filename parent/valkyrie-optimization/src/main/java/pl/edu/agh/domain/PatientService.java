package pl.edu.agh.domain;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.dao.PatientDao;

import java.util.ArrayList;
import java.util.List;

public class PatientService {

    @Autowired
    private PatientDao patientDao;

    public List<Patient> findPatients(final PatientFilter filter) {
        List<Patient> filtered = new ArrayList<Patient>();
        for (Patient supplier : patientDao.getAll()) {
            if (checkFilter(supplier, filter)) {
                filtered.add(supplier);
            }
        }
        return filtered;
    }

    public Patient create(Patient patient) {
        return patientDao.create(patient);
    }

    public Patient update(Patient patient) {
        return patientDao.update(patient);
    }

    public void delete(Patient patient) {
        patientDao.delete(patient);
    }

    public Patient getPatient(Integer id) {
        return patientDao.get(id);
    }

    public boolean checkFilter(Patient supplier, PatientFilter filter) {
        boolean nameOk = true;
        boolean contactNameOk = true;
        if (filter.getPeselContains() != null)
            nameOk = supplier.getPesel().contains(filter.getPeselContains());
        return nameOk && contactNameOk;
    }
}
