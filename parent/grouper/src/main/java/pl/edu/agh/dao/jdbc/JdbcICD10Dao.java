package pl.edu.agh.dao.jdbc;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import pl.edu.agh.dao.ICD10Dao;
import pl.edu.agh.dao.jdbc.mapper.ICD10Mapper;
import pl.edu.agh.domain.ICD10;
import pl.edu.agh.domain.ICD10Filter;

import java.util.ArrayList;
import java.util.List;


public class JdbcICD10Dao extends SimpleJdbcDaoSupport implements ICD10Dao {
    private final static String SELECT_SQL = "SELECT code,name FROM icd10";
    private final static String SELECT_BY_CODE_SQL = SELECT_SQL + " WHERE code = ?";

    private final static ICD10Mapper MAPPER = new ICD10Mapper();

    @Override
    public List<ICD10> getList(ICD10Filter filter) {
        StringBuilder sql = new StringBuilder(SELECT_SQL);
        List<String> args = new ArrayList<String>();
        boolean or = false;

        if (StringUtils.isNotBlank(filter.getCode())) {
            sql.append(SQLBuilderUtil.orLike("code", or));
            args.add("%" + filter.getCode() + "%");
            or = true;
        }
        if (StringUtils.isNotBlank(filter.getName())) {
            sql.append(SQLBuilderUtil.andLike("name", or));
            args.add("%" + filter.getName() + "%");
            or = true;
        }
        return getJdbcTemplate().query(sql.toString(), args.toArray(), MAPPER);
    }

    @Override
    public ICD10 getByCode(String code) {
        return getJdbcTemplate().queryForObject(SELECT_BY_CODE_SQL, new Object[]{code}, MAPPER);
    }
}
