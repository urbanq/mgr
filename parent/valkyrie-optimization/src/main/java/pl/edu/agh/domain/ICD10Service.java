package pl.edu.agh.domain;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.dao.ICD10Dao;

import java.util.List;

public class ICD10Service {
    @Autowired
    private ICD10Dao icd10Dao;

    public List<ICD10> findICD10(final ICD10Filter filter) {
        return icd10Dao.getList(filter);
    }
}
