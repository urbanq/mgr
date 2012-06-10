package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.domain.IncomeModeLimit;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public class IncomeModeLimitMapper implements RowMapper<IncomeModeLimit> {
    @Override
    public IncomeModeLimit mapRow(ResultSet rs, int rowNum) throws SQLException {
        IncomeModeLimit incomeModeLimit = new IncomeModeLimit();
        incomeModeLimit.setId(rs.getInt("id"));
        incomeModeLimit.setName(rs.getString("name"));
        return incomeModeLimit;
    }
}