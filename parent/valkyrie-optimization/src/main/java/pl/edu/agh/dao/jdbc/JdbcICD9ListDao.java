package pl.edu.agh.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import pl.edu.agh.dao.ICD9ListDao;
import pl.edu.agh.dao.jdbc.mapper.ICD9ListMapper;
import pl.edu.agh.domain.ICD9;
import pl.edu.agh.domain.ICD9List;

import java.util.List;

/**
 * @author mateusz
 * @date 03.06.12
 */
public class JdbcICD9ListDao extends SimpleJdbcDaoSupport implements ICD9ListDao {
    private final static String SELECT_SQL = "SELECT * FROM icd9_list_code";
    private final static String SELECT_BY_CODE_SQL = SELECT_SQL + " WHERE icd9_code = ?";

    @Autowired
    private ICD9ListMapper mapper;

    @Override
    public List<ICD9List> getListCodes(ICD9 icd9) {
        return getJdbcTemplate().query(SELECT_BY_CODE_SQL, new Object[]{icd9.getCode()}, mapper);
    }
}
