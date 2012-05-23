package pl.edu.agh.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.dao.JGPDao;
import pl.edu.agh.domain.Grouper;
import pl.edu.agh.domain.JGP;
import pl.edu.agh.domain.JGPFilter;
import pl.edu.agh.domain.JGPResult;

import java.util.List;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class JGPService {
    @Autowired
    private JGPDao jgpDao;

    public List<JGP> findJGP(final JGPFilter filter) {
        return jgpDao.getList(filter);
    }

    public JGPResult group(Grouper grouper) {

        return null;
    }
}
