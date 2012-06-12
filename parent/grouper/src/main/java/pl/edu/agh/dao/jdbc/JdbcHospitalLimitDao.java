package pl.edu.agh.dao.jdbc;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import pl.edu.agh.dao.HospitalLimitDao;
import pl.edu.agh.dao.jdbc.mapper.HospitalLimitMapper;
import pl.edu.agh.domain.HospitalLimit;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * User: mateusz
 * Date: 27.05.12
 */
public class JdbcHospitalLimitDao extends SimpleJdbcDaoSupport implements HospitalLimitDao {
    private final static String SELECT_SQL = "SELECT id,under,over,time_unit FROM hospital_limit";
    private final static String SELECT_BY_ID_SQL = SELECT_SQL + " WHERE id = ?";
    private final static HospitalLimitMapper MAPPER = new HospitalLimitMapper();

    @Override
    public HospitalLimit get(Integer id) {
        return new HospitalLimitMappingQuery(getDataSource()).findObject(id);
    }

    @Override
    public List<HospitalLimit> getAll() {
        return getJdbcTemplate().query(SELECT_SQL, MAPPER);
    }

    private static final class HospitalLimitMappingQuery extends MappingSqlQuery<HospitalLimit> {
        public HospitalLimitMappingQuery(DataSource ds) {
            super(ds, SELECT_BY_ID_SQL);
            super.declareParameter(new SqlParameter("id", Types.INTEGER));
            compile();
        }

        @Override
        public HospitalLimit mapRow(ResultSet rs, int rowNumber) throws SQLException {
            return MAPPER.mapRow(rs, rowNumber);
        }
    }
}