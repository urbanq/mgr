package pl.edu.agh.dao;

import pl.edu.agh.domain.JGP;
import pl.edu.agh.domain.JGPValue;

import java.util.List;

/**
 * User: mateusz
 * Date: 02.06.12
 */
public interface JGPValueDao {
    List<JGPValue> getByJGP(JGP jgp);
}
