package pl.edu.agh.service;

import pl.edu.agh.domain.ICD9;
import pl.edu.agh.domain.ICD9Filter;

import java.util.List;

/**
 * @author mateusz
 * @date 11.07.12
 */
public interface ICD9Service {

    public List<ICD9> findICD9(final ICD9Filter filter);
}
