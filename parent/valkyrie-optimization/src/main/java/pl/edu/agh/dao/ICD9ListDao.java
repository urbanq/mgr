package pl.edu.agh.dao;

import pl.edu.agh.domain.ICD9;
import pl.edu.agh.domain.ICD9List;

import java.util.List;

/**
 * @author mateusz
 * @date 03.06.12
 */
public interface ICD9ListDao {
    List<ICD9List> getListCodes(ICD9 icd9);
}
