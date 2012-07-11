package pl.edu.agh.service;

import pl.edu.agh.domain.Patient;
import pl.edu.agh.domain.PatientFilter;

import java.util.List;

/**
 * @author mateusz
 * @date 11.07.12
 */
public interface PatientService {

    public List<Patient> findPatients(final PatientFilter filter);

    public Patient create(Patient patient);
    public Patient update(Patient patient);
    public void delete(Patient patient);

    public Patient getPatient(Integer id);
}
