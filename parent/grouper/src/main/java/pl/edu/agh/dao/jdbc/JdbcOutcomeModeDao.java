package pl.edu.agh.dao.jdbc;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import pl.edu.agh.dao.OutcomeModeDao;
import pl.edu.agh.dao.jdbc.mapper.OutcomeModeMapper;
import pl.edu.agh.domain.OutcomeMode;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public class JdbcOutcomeModeDao extends SimpleJdbcDaoSupport implements OutcomeModeDao {
    private final static String SELECT_SQL = "SELECT id,name FROM outcome_mode_limit";
    private final static String SELECT_BY_ID_SQL = SELECT_SQL + " WHERE id = ?";

    private final static OutcomeModeMapper MAPPER = new OutcomeModeMapper();

    @Override
    public OutcomeMode get(Integer id) {
        return new OutcomeModeLimitMappingQuery(getDataSource()).findObject(id);
    }

    @Override
    public List<OutcomeMode> getAll() {
        return getJdbcTemplate().query(SELECT_SQL, MAPPER);
    }

    private static final class OutcomeModeLimitMappingQuery extends MappingSqlQuery<OutcomeMode> {
        public OutcomeModeLimitMappingQuery(DataSource ds) {
            super(ds, SELECT_BY_ID_SQL);
            super.declareParameter(new SqlParameter("id", Types.INTEGER));
            compile();
        }

        @Override
        public OutcomeMode mapRow(ResultSet rs, int rowNumber) throws SQLException {
            return MAPPER.mapRow(rs, rowNumber);
        }
    }
}
