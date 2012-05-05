package pl.edu.agh.dao.jdbc;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import pl.edu.agh.dao.PatientDao;
import pl.edu.agh.domain.Patient;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;


public class JdbcPatientDao extends SimpleJdbcDaoSupport implements PatientDao {

    private final static String INSERT_SQL = "INSERT INTO patient (first_name, last_name, pesel) VALUES (?, ?, ?)";
    private final static String UPDATE_SQL = "UPDATE patient SET first_name = ?, last_name = ?, pesel = ? WHERE id = ?";
    private final static String DELETE_SQL = "DELETE FROM patient WHERE id = ?";
    private final static String SELECT_SQL = "SELECT id, first_name, last_name, pesel FROM patient";
    private final static String SELECT_BY_ID_SQL = SELECT_SQL + " WHERE id = ?";

    @Override
    public Patient create(final Patient patient) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        getJdbcTemplate().update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                        ps.setString(1, patient.getFirstName());
                        ps.setString(2, patient.getLastName());
                        ps.setString(3, patient.getPesel());
                        return ps;
                    }
                },
                keyHolder);
        int id = keyHolder.getKey().intValue();
        return get(id);
    }

    @Override
    public Patient update(Patient patient) {
        getJdbcTemplate().update(UPDATE_SQL,
                                patient.getFirstName(),
                                patient.getLastName(),
                                patient.getPesel(),
                                patient.getId());
        return patient;
    }

    @Override
    public void delete(Patient patient) {
        getJdbcTemplate().update(DELETE_SQL, patient.getId());
    }

    @Override
    public Patient get(Integer id) {
        return new PatientMappingQuery(getDataSource()).findObject(id);
    }

    @Override
    public List<Patient> getAll() {
        return getJdbcTemplate().query(SELECT_SQL, new PatientMapper());
    }

    private class PatientMappingQuery extends MappingSqlQuery<Patient> {
        private PatientMapper mapper = new PatientMapper();

        public PatientMappingQuery(DataSource ds) {
            super(ds, SELECT_BY_ID_SQL);
            super.declareParameter(new SqlParameter("id", Types.INTEGER));
            compile();
        }

        @Override
        public Patient mapRow(ResultSet rs, int rowNumber) throws SQLException {
            return mapper.mapRow(rs, rowNumber);
        }
    }

    private static final class PatientMapper implements RowMapper<Patient> {
        @Override
        public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
            Patient patient = new Patient();
            patient.setId(rs.getInt("id"));
            patient.setFirstName(rs.getString("first_name"));
            patient.setLastName(rs.getString("last_name"));
            patient.setPesel(rs.getString("pesel"));
            return patient;
        }
    }
}