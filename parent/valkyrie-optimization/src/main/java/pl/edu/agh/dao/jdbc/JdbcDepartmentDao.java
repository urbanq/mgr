package pl.edu.agh.dao.jdbc;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import pl.edu.agh.dao.DepartmentDao;
import pl.edu.agh.dao.jdbc.mapper.DepartmentMapper;
import pl.edu.agh.domain.Department;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class JdbcDepartmentDao extends SimpleJdbcDaoSupport implements DepartmentDao {
    private final static String SELECT_SQL = "SELECT id,name FROM department";
    private final static String SELECT_BY_ID_SQL = SELECT_SQL + " WHERE id = ?";

    private final static DepartmentMapper MAPPER = new DepartmentMapper();

    @Override
    public Department get(String id) {
        return new DepartmentMappingQuery(getDataSource()).findObject(id);
    }

    @Override
    public List<Department> getAll() {
        return getJdbcTemplate().query(SELECT_SQL, MAPPER);
    }

    private static final class DepartmentMappingQuery extends MappingSqlQuery<Department> {
        public DepartmentMappingQuery(DataSource ds) {
            super(ds, SELECT_BY_ID_SQL);
            super.declareParameter(new SqlParameter("id", Types.CHAR));
            compile();
        }

        @Override
        public Department mapRow(ResultSet rs, int rowNumber) throws SQLException {
            return MAPPER.mapRow(rs, rowNumber);
        }
    }
}
