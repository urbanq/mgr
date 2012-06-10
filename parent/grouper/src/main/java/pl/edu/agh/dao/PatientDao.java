package pl.edu.agh.dao;

import pl.edu.agh.domain.Patient;
import pl.edu.agh.domain.PatientFilter;

public interface PatientDao extends GenericDao<Patient, Integer>,
                                     FilterDao<Patient, PatientFilter> {
}
