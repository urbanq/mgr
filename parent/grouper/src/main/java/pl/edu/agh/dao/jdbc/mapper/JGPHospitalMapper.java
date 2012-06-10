package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.dao.JGPDao;
import pl.edu.agh.domain.JGPHospital;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author mateusz
 * @date 10.06.12
 */
public class JGPHospitalMapper implements RowMapper<JGPHospital> {
    @Autowired
    private JGPDao jgpDao;

    @Override
    public JGPHospital mapRow(ResultSet rs, int rowNum) throws SQLException {
        JGPHospital jgpHospital = new JGPHospital();
        jgpHospital.setJgp(jgpDao.getByCode(rs.getString("jgp_code")));
        jgpHospital.setDays(rs.getInt("days"));
        jgpHospital.setUnderValue(rs.getDouble("value_under"));
        jgpHospital.setOverValue(rs.getDouble("value_over"));
        return jgpHospital;
    }
}
