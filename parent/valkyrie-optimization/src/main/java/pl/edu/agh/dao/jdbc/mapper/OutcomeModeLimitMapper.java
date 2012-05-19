package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.domain.OutcomeModeLimit;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public class OutcomeModeLimitMapper  implements RowMapper<OutcomeModeLimit> {
    @Override
    public OutcomeModeLimit mapRow(ResultSet rs, int rowNum) throws SQLException {
        OutcomeModeLimit outcomeModeLimit = new OutcomeModeLimit();
        outcomeModeLimit.setId(rs.getInt("id"));
        outcomeModeLimit.setName(rs.getString("name"));
        return outcomeModeLimit;
    }
}