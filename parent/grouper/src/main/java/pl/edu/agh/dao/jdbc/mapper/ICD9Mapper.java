package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.domain.ICD9;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ICD9Mapper implements RowMapper<ICD9> {
    @Override
    public ICD9 mapRow(ResultSet rs, int rowNum) throws SQLException {
        ICD9 icd9 = new ICD9();
        icd9.setCode(rs.getString("code"));
        icd9.setName(rs.getString("name"));
        icd9.setRange(rs.getInt("range"));
        return icd9;
    }
}
