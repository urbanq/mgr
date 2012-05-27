package pl.edu.agh.dao;

import pl.edu.agh.domain.ICD9;
import pl.edu.agh.domain.JGPParameter;

import java.util.List;

/**
 * User: mateusz
 * Date: 27.05.12
 */
public interface JGPParameterDao {
    List<JGPParameter> getByProcedure(ICD9 icd9);
}
