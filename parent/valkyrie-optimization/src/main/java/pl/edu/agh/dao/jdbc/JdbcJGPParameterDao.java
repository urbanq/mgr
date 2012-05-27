package pl.edu.agh.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import pl.edu.agh.dao.JGPParameterDao;
import pl.edu.agh.dao.jdbc.mapper.JGPParameterMapper;
import pl.edu.agh.domain.ICD9;
import pl.edu.agh.domain.JGPParameter;
import pl.edu.agh.domain.JGPParameter.ListType;

import java.util.List;

/**
 * User: mateusz
 * Date: 27.05.12
 */
public class JdbcJGPParameterDao extends SimpleJdbcDaoSupport implements JGPParameterDao {
    @Autowired
    private JGPParameterMapper mapper;

    /**
     * join with icd9_list_code table
     */
    private final static String SELECT_BY_ICD9_CODE = "SELECT * FROM JGP_PARAMETER p JOIN icd9_list_code i on (p.list_code = i.list_code) where p.list_type=? and i.icd9_code=?";


    @Override
    public List<JGPParameter> getByProcedure(ICD9 icd9) {
        return getJdbcTemplate().query(SELECT_BY_ICD9_CODE, new Object[]{ListType.ICD9.name(), icd9.getCode()}, mapper) ;
    }
}
