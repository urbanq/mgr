package pl.edu.agh.dao.jdbc;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import pl.edu.agh.dao.JGPDao;
import pl.edu.agh.dao.jdbc.mapper.JGPMapper;
import pl.edu.agh.domain.JGP;
import pl.edu.agh.domain.JGPFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class JdbcJGPDao extends SimpleJdbcDaoSupport implements JGPDao {
    private final static String SELECT_SQL = "SELECT code,product_code,name FROM jgp";

    private final static JGPMapper MAPPER = new JGPMapper();

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
}
