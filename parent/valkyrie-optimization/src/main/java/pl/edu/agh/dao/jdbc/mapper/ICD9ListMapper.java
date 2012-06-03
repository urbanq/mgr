package pl.edu.agh.dao.jdbc.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import pl.edu.agh.dao.ICD9Dao;
import pl.edu.agh.domain.ICD9List;
import pl.edu.agh.domain.ListType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author mateusz
 * @date 03.06.12
 */
public class ICD9ListMapper implements RowMapper<ICD9List> {
    @Autowired
    private ICD9Dao icd9Dao;

    @Override
    public ICD9List mapRow(ResultSet rs, int rowNum) throws SQLException {
        ICD9List icd9List = new ICD9List();
        icd9List.setIcd9(icd9Dao.getByCode(rs.getString("icd9_code")));
        icd9List.setListCode(rs.getString("list_code"));
        icd9List.setListType(ListType.valueOf(rs.getString("list_type").charAt(0)));
        return icd9List;
    }
}
