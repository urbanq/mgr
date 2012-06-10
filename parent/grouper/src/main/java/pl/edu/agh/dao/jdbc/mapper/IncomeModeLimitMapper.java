package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.domain.IncomeMode;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public class IncomeModeLimitMapper implements RowMapper<IncomeMode> {
    @Override
    public IncomeMode mapRow(ResultSet rs, int rowNum) throws SQLException {
        IncomeMode incomeMode = new IncomeMode();
        incomeMode.setId(rs.getInt("id"));
        incomeMode.setName(rs.getString("name"));
        return incomeMode;
    }
}