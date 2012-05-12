package pl.edu.agh.domain;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.dao.ICD9Dao;

import java.util.List;

/**
 * User: mateusz
 * Date: 12.05.12
 */
public class ICD9Service {
    @Autowired
    private ICD9Dao icd9Dao;

    public List<ICD9> findICD9(final ICD9Filter filter) {
        return icd9Dao.getList(filter);
    }
}
