package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.domain.ListType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class ListTypeMapper implements RowMapper<ListType> {
    @Override
    public ListType mapRow(ResultSet rs, int rowNum) throws SQLException {
        ListType listType = new ListType();
        listType.setId(rs.getString("id").charAt(0));
        listType.setName(rs.getString("name"));
        return listType;
    }
}
