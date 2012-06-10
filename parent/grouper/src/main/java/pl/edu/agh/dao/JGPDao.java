package pl.edu.agh.dao;

import pl.edu.agh.domain.JGP;
import pl.edu.agh.domain.JGPFilter;
import pl.edu.agh.domain.JGPHospital;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public interface JGPDao extends FilterDao<JGP, JGPFilter> {
    JGP getByCode(String code);
    JGPHospital getHospital(JGP jgp);
}
