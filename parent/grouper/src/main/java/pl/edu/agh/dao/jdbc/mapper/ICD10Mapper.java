package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.domain.ICD10;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ICD10Mapper implements RowMapper<ICD10> {
    @Override
    public ICD10 mapRow(ResultSet rs, int rowNum) throws SQLException {
        ICD10 icd10 = new ICD10();
        icd10.setCode(rs.getString("code"));
        icd10.setName(rs.getString("name"));
        return icd10;
    }
}