package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.domain.JGP;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class JGPMapper implements RowMapper<JGP> {
    @Override
    public JGP mapRow(ResultSet rs, int rowNum) throws SQLException {
        JGP jgp = new JGP();
        jgp.setCode(rs.getString("code"));
        jgp.setProductCode(rs.getString("product_code"));
        jgp.setName(rs.getString("name"));
        return jgp;
    }
}
