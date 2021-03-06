package pl.edu.agh.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.dao.PatientDao;
import pl.edu.agh.domain.Patient;
import pl.edu.agh.domain.PatientFilter;
import pl.edu.agh.service.PatientService;

import java.util.List;

public class PatientServiceImpl implements PatientService {

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
