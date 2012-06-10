package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.dao.*;
import pl.edu.agh.domain.Condition;
import pl.edu.agh.domain.JGPParameter;
import pl.edu.agh.domain.Sex;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mateusz
 * Date: 27.05.12
 */
public class JGPParameterMapper implements RowMapper<JGPParameter> {

    @Autowired
    private JGPDao jgpDao;
    @Autowired
    private HospitalLimitDao hospitalLimitDao;
    @Autowired
    private AgeLimitDao ageLimitDao;
    @Autowired
    private IncomeModeLimitDao incomeModeLimitDao;
    @Autowired
    private OutcomeModeLimitDao outcomeModeLimitDao;

    @Override
    public JGPParameter mapRow(ResultSet rs, int rowNum) throws SQLException {
        JGPParameter parameter = new JGPParameter();
        parameter.setListType(JGPParameter.ListType.valueOf(rs.getString("list_type")));
        parameter.setListCode(rs.getString("list_code"));
        parameter.setJgp(jgpDao.getByCode(rs.getString("jgp_code")));
        parameter.setCondition(Condition.valueOf(rs.getString("algorithm_code").charAt(0)));
        //limitations
        parameter.setHospitalLimit(rs.getInt("hospital_limit") == 0 ? null : hospitalLimitDao.get(rs.getInt("hospital_limit")));
        parameter.setAgeLimit(rs.getInt("age_limit") == 0 ? null : ageLimitDao.get(rs.getInt("age_limit")));
        parameter.setSexLimit(rs.getString("limit_sex") == null ? null : Sex.valueOf(rs.getString("limit_sex").charAt(0)));
        parameter.setIncomeMode(rs.getInt("income_mode_limit") == 0 ? null : incomeModeLimitDao.get(rs.getInt("income_mode_limit")));
        parameter.setOutcomeMode(rs.getInt("outcome_mode_limit") == 0 ? null : outcomeModeLimitDao.get(rs.getInt("outcome_mode_limit")));
        // conditions
        parameter.setFirstICD9ListCode(rs.getString("cond1_icd9_list_code"));
        parameter.setFirstICD9MinimalCount(rs.getInt("cond1_icd9_count"));
        parameter.setSecondICD9ListCode(rs.getString("cond2_icd9_list_code"));
        parameter.setSecondICD9MinimalCount(rs.getInt("cond2_icd9_count"));
        parameter.setMainICD10ListCode(rs.getString("cond_main_icd10_list_code"));
        parameter.setFirstICD10ListCode(rs.getString("cond1_icd10_list_code"));
        parameter.setSecondICD10ListCode(rs.getString("cond2_icd10_list_code"));
        parameter.setNegativeICD9ListCode(rs.getString("negative_icd9_list_code"));
        parameter.setNegativeICD10ListCode(rs.getString("negative_icd10_list_code"));
        return parameter;
    }
}
