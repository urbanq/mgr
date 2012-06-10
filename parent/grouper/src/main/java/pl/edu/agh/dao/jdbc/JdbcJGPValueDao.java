package pl.edu.agh.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import pl.edu.agh.dao.JGPValueDao;
import pl.edu.agh.dao.jdbc.mapper.JGPValueMapper;
import pl.edu.agh.domain.JGP;
import pl.edu.agh.domain.JGPValue;

/**
 * User: mateusz
 * Date: 02.06.12
 */
public class JdbcJGPValueDao extends SimpleJdbcDaoSupport implements JGPValueDao {
    @Autowired
    private JGPValueMapper mapper;

    /**
     * join with
     */
    private final static String SELECT_BY_JGP_CODE = "SELECT * FROM JGP_POINT_VALUE where jgp_code=?";

    @Override
    public JGPValue getByJGP(JGP jgp) {
        return getJdbcTemplate().queryForObject(SELECT_BY_JGP_CODE, new Object[]{jgp.getCode()}, mapper);
    }
}
