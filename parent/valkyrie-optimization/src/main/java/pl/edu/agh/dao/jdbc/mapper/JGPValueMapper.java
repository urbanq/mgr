package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.dao.JGPDao;
import pl.edu.agh.domain.HospitalType;
import pl.edu.agh.domain.JGPValue;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * User: mateusz
 * Date: 02.06.12
 */
public class JGPValueMapper implements RowMapper<JGPValue> {
    @Autowired
    private JGPDao jgpDao;

    @Override
    public JGPValue mapRow(ResultSet rs, int rowNum) throws SQLException {
        JGPValue value = new JGPValue();
        value.setJgp(jgpDao.getByCode(rs.getString("jgp_code")));
        value.setValue(HospitalType.NORMAL, rs.getDouble("value_hosp"));
        value.setValue(HospitalType.PLANNED, rs.getDouble("value_planned_hosp"));
        value.setValue(HospitalType.ONE_DAY, rs.getDouble("value_one_day"));
        return value;
    }
}
