package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.domain.AgeLimit;
import pl.edu.agh.domain.TimeUnit;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class AgeLimitMapper implements RowMapper<AgeLimit> {
    @Override
    public AgeLimit mapRow(ResultSet rs, int rowNum) throws SQLException {
        AgeLimit ageLimit = new AgeLimit();
        ageLimit.setId(rs.getInt("id"));
        ageLimit.setUnder(rs.getInt("under"));
        ageLimit.setOver(rs.getInt("over"));
        ageLimit.setTimeUnit(TimeUnit.valueOf(rs.getString("time_unit").charAt(0)));
        return ageLimit;
    }
}
