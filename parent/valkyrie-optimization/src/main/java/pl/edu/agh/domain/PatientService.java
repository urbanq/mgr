package pl.edu.agh.domain;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.dao.PatientDao;

import java.util.List;

public class PatientService {

    @Autowired
    private PatientDao patientDao;

    public List<Patient> findPatients(final PatientFilter filter) {
        return patientDao.getList(filter);
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
}
