package pl.edu.agh.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.dao.ICD10Dao;
import pl.edu.agh.domain.ICD10;
import pl.edu.agh.domain.ICD10Filter;

import java.util.List;

public class ICD10Service {
    @Autowired
    private ICD10Dao icd10Dao;

    public List<ICD10> findICD10(final ICD10Filter filter) {
        return icd10Dao.getList(filter);
    }
}
