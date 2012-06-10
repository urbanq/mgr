package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.domain.Department;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class DepartmentMapper implements RowMapper<Department> {
    @Override
    public Department mapRow(ResultSet rs, int rowNum) throws SQLException {
        Department department = new Department();
        department.setId(rs.getString("id"));
        department.setName(rs.getString("name"));
        return department;
    }
}
