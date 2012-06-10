package pl.edu.agh.dao;

import pl.edu.agh.domain.JGP;
import pl.edu.agh.domain.JGPValue;

/**
 * User: mateusz
 * Date: 02.06.12
 */
public interface JGPValueDao {
    JGPValue getByJGP(JGP jgp);
}
