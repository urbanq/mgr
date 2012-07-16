package pl.edu.agh.dao;

import pl.edu.agh.domain.ICD9;

import java.util.List;

/**
 * @author mateusz
 * @date 03.06.12
 */
public interface ICD9ListDao {
    /**
     * get list of codes for icd9 code
     */
    List<String> getListCodes(ICD9 icd9);

    /**
     * @param listCode
     * @return list of icd9 codes for listCode
     */
    List<String> getICD9Codes(String listCode);

    /**
     * get joint list code for argument icd9 codes
     */
    List<String> getListCodes(ICD9 firstICD9, ICD9 secondICD9);
}
