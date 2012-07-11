package pl.edu.agh.service;

import pl.edu.agh.domain.ICD10;
import pl.edu.agh.domain.ICD10Filter;

import java.util.List;

/**
 * @author mateusz
 * @date 11.07.12
 */
public interface ICD10Service {

    public List<ICD10> findICD10(final ICD10Filter filter);
}
