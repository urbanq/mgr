package pl.edu.agh.dao.jdbc;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import pl.edu.agh.dao.ICD9Dao;
import pl.edu.agh.dao.jdbc.mapper.ICD9Mapper;
import pl.edu.agh.domain.ICD9;
import pl.edu.agh.domain.ICD9Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mateusz
 * Date: 12.05.12
 */
public class JdbcICD9Dao extends SimpleJdbcDaoSupport implements ICD9Dao {
    private final static String SELECT_SQL = "SELECT code,name,range FROM icd9";

    private final static ICD9Mapper MAPPER = new ICD9Mapper();

    @Override
    public List<ICD9> getList(ICD9Filter filter) {
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
}
