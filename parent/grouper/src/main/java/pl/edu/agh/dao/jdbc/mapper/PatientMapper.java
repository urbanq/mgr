package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.domain.Patient;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientMapper implements RowMapper<Patient> {
    @Override
    public Patient mapRow(ResultSet rs, int rowNum) throws SQLException {
        Patient patient = new Patient();
        patient.setId(rs.getInt("id"));
        patient.setFirstname(rs.getString("first_name"));
        patient.setLastname(rs.getString("last_name"));
        patient.setPesel(rs.getString("pesel"));
        return patient;
    }
}