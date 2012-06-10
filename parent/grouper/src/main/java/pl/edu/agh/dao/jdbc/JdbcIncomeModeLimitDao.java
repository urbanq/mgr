package pl.edu.agh.dao.jdbc;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import pl.edu.agh.dao.IncomeModeLimitDao;
import pl.edu.agh.dao.jdbc.mapper.IncomeModeLimitMapper;
import pl.edu.agh.domain.IncomeMode;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public class JdbcIncomeModeLimitDao extends SimpleJdbcDaoSupport implements IncomeModeLimitDao {
    private final static String SELECT_SQL = "SELECT id,name FROM income_mode_limit";
    private final static String SELECT_BY_ID_SQL = SELECT_SQL + " WHERE id = ?";

    private final static IncomeModeLimitMapper MAPPER = new IncomeModeLimitMapper();

    @Override
    public IncomeMode get(Integer id) {
        return new IncomeModeLimitMappingQuery(getDataSource()).findObject(id);
    }

    @Override
    public List<IncomeMode> getAll() {
        return getJdbcTemplate().query(SELECT_SQL, MAPPER);
    }

    private static final class IncomeModeLimitMappingQuery extends MappingSqlQuery<IncomeMode> {
        public IncomeModeLimitMappingQuery(DataSource ds) {
            super(ds, SELECT_BY_ID_SQL);
            super.declareParameter(new SqlParameter("id", Types.INTEGER));
            compile();
        }

        @Override
        public IncomeMode mapRow(ResultSet rs, int rowNumber) throws SQLException {
            return MAPPER.mapRow(rs, rowNumber);
        }
    }
}
