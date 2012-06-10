package pl.edu.agh.dao.jdbc;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import pl.edu.agh.dao.JGPDao;
import pl.edu.agh.dao.jdbc.mapper.JGPHospitalMapper;
import pl.edu.agh.dao.jdbc.mapper.JGPMapper;
import pl.edu.agh.domain.JGP;
import pl.edu.agh.domain.JGPFilter;
import pl.edu.agh.domain.JGPHospital;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class JdbcJGPDao extends SimpleJdbcDaoSupport implements JGPDao {
    private final static String SELECT_SQL = "SELECT code,product_code,name FROM jgp";
    private final static String SELECT_BY_CODE_SQL = SELECT_SQL + " WHERE code = ?";

    private final static JGPMapper MAPPER = new JGPMapper();
    @Autowired
    private JGPHospitalMapper jgpHospitalMapper;

    @Override
    public List<JGP> getList(JGPFilter filter) {
        StringBuilder sql = new StringBuilder(SELECT_SQL);
        List<String> args = new ArrayList<String>();
        boolean and = false;

        if (StringUtils.isNotBlank(filter.getCode())) {
            sql.append(SQLBuilderUtil.andLike("code", and));
            args.add("%" + filter.getCode() + "%");
            and = true;
        }
        if (StringUtils.isNotBlank(filter.getName())) {
            sql.append(SQLBuilderUtil.andLike("name", and));
            args.add("%" + filter.getName() + "%");
            and = true;
        }
        return getJdbcTemplate().query(sql.toString(), args.toArray(), MAPPER);
    }

    @Override
    public JGP getByCode(String code) {
        try {
            return getJdbcTemplate().queryForObject(SELECT_BY_CODE_SQL, MAPPER, code);
        } catch(EmptyResultDataAccessException emptyResultDataAccessException) {
            return null;
        }
    }

    private final static String SELECT_HOSPITAL_SQL = "SELECT * FROM jgp_hospital WHERE jgp_code = ?";

    @Override
    public JGPHospital getHospital(JGP jgp) {
        try {
            return getJdbcTemplate().queryForObject(SELECT_HOSPITAL_SQL, jgpHospitalMapper, jgp.getCode());
        } catch(EmptyResultDataAccessException emptyResultDataAccessException) {
            return null;
        }
    }
}
