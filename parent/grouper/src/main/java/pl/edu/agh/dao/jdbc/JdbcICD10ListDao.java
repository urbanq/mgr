package pl.edu.agh.dao.jdbc;

import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import pl.edu.agh.dao.ICD10ListDao;
import pl.edu.agh.domain.ICD10;

import java.util.List;

/**
 * @author mateusz
 * @date 07.06.12
 */
public class JdbcICD10ListDao extends SimpleJdbcDaoSupport implements ICD10ListDao {
    private final static String SELECT_SQL = "SELECT list_code FROM icd10_list_code";
    private final static String SELECT_BY_CODE_SQL = SELECT_SQL + " WHERE icd10_code = ?";

    @Override
    public List<String> getListCodes(ICD10 icd10) {
        return getJdbcTemplate().queryForList(SELECT_BY_CODE_SQL, String.class, icd10.getCode());
    }

    private final static String SELECT_LIST_CODES_SQL = "SELECT lc1.list_code FROM icd10_list_code lc1 " +
                                                        "INNER JOIN icd10_list_code lc2 on (lc1.list_code = lc2.list_code) " +
                                                        "WHERE lc1.icd10_code = ? and lc2.icd10_code = ?";

    @Override
    public List<String> getListCodes(ICD10 firstICD10, ICD10 secondICD10) {
        return getJdbcTemplate().queryForList(SELECT_LIST_CODES_SQL, String.class, firstICD10.getCode(), secondICD10.getCode());
    }
}
