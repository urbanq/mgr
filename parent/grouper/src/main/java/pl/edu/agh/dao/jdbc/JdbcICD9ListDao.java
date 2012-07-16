package pl.edu.agh.dao.jdbc;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import pl.edu.agh.dao.ICD9ListDao;
import pl.edu.agh.domain.ICD9;

import java.util.List;

/**
 * @author mateusz
 * @date 03.06.12
 */
public class JdbcICD9ListDao extends SimpleJdbcDaoSupport implements ICD9ListDao {
    private final static String SELECT_BY_ICD9_CODE = "SELECT list_code FROM icd9_list_code WHERE icd9_code = ?";

    @Override
    public List<String> getListCodes(ICD9 icd9) {
        return getJdbcTemplate().queryForList(SELECT_BY_ICD9_CODE, String.class, icd9.getCode());
    }

    private final static String SELECT_BY_LIST_CODE = "SELECT icd9_code FROM icd9_list_code WHERE list_code = ?";

    @Override
    public List<String> getICD9Codes(String listCode) {
        return getJdbcTemplate().queryForList(SELECT_BY_LIST_CODE, String.class, listCode);
    }

    private final static String SELECT_LIST_CODES_SQL = "SELECT lc1.list_code FROM icd9_list_code lc1 " +
                                                        "INNER JOIN icd9_list_code lc2 on (lc1.list_code = lc2.list_code) " +
                                                        "WHERE lc1.icd9_code = ? and lc2.icd9_code = ?";
    @Override
    public List<String> getListCodes(ICD9 firstICD9, ICD9 secondICD9) {
        return getJdbcTemplate().queryForList(SELECT_LIST_CODES_SQL, String.class, firstICD9.getCode(), secondICD9.getCode());
    }
}
