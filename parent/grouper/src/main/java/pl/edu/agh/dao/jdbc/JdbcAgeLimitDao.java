package pl.edu.agh.dao.jdbc;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import pl.edu.agh.dao.AgeLimitDao;
import pl.edu.agh.dao.jdbc.mapper.AgeLimitMapper;
import pl.edu.agh.domain.AgeLimit;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * User: mateusz
 * Date: 27.05.12
 */
public class JdbcAgeLimitDao extends SimpleJdbcDaoSupport implements AgeLimitDao {
    private final static String SELECT_SQL = "SELECT id,under,over,time_unit FROM age_limit";
    private final static String SELECT_BY_ID_SQL = SELECT_SQL + " WHERE id = ?";
    private final static AgeLimitMapper MAPPER = new AgeLimitMapper();

    @Override
    public AgeLimit get(Integer id) {
        return new AgeLimitMappingQuery(getDataSource()).findObject(id);
    }

    @Override
    public List<AgeLimit> getAll() {
        return getJdbcTemplate().query(SELECT_SQL, MAPPER);
    }

    private static final class AgeLimitMappingQuery extends MappingSqlQuery<AgeLimit> {
        public AgeLimitMappingQuery(DataSource ds) {
            super(ds, SELECT_BY_ID_SQL);
            super.declareParameter(new SqlParameter("id", Types.INTEGER));
            compile();
        }

        @Override
        public AgeLimit mapRow(ResultSet rs, int rowNumber) throws SQLException {
            return MAPPER.mapRow(rs, rowNumber);
        }
    }
}
