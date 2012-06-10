package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.domain.OutcomeMode;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public class OutcomeModeMapper implements RowMapper<OutcomeMode> {
    @Override
    public OutcomeMode mapRow(ResultSet rs, int rowNum) throws SQLException {
        OutcomeMode outcomeMode = new OutcomeMode();
        outcomeMode.setId(rs.getInt("id"));
        outcomeMode.setName(rs.getString("name"));
        return outcomeMode;
    }
}