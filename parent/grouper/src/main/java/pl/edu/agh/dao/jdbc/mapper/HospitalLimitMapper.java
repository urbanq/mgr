package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.domain.HospitalLimit;
import pl.edu.agh.domain.TimeUnit;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class HospitalLimitMapper implements RowMapper<HospitalLimit> {
    @Override
    public HospitalLimit mapRow(ResultSet rs, int rowNum) throws SQLException {
        HospitalLimit hospitalLimit = new HospitalLimit();
        hospitalLimit.setId(rs.getInt("id"));
        hospitalLimit.setUnder(rs.getObject("under") != null ? (Integer)rs.getObject("under") : null);
        hospitalLimit.setOver(rs.getObject("over") != null ? (Integer)rs.getObject("over") : null);
        hospitalLimit.setTimeUnit(TimeUnit.valueOf(rs.getString("time_unit").charAt(0)));
        return hospitalLimit;
    }
}
