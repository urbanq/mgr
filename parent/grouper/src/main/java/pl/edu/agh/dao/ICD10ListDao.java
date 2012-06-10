package pl.edu.agh.dao;

import pl.edu.agh.domain.ICD10;

import java.util.List;

/**
 * @author mateusz
 * @date 07.06.12
 */
public interface ICD10ListDao {
    /**
     * get list of codes for icd9 code
     */
    List<String> getListCodes(ICD10 icd10);

    /**
     * get joint list code for argument icd10 codes
     */
    List<String> getListCodes(ICD10 firstICD10, ICD10 secondICD10);
}
