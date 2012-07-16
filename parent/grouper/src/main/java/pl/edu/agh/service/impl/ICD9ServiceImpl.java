package pl.edu.agh.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.dao.ICD9Dao;
import pl.edu.agh.dao.ICD9ListDao;
import pl.edu.agh.domain.ICD9;
import pl.edu.agh.domain.ICD9Filter;
import pl.edu.agh.service.ICD9Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mateusz
 * Date: 12.05.12
 */
public class ICD9ServiceImpl implements ICD9Service {
    @Autowired
    private ICD9Dao icd9Dao;
    @Autowired
    private ICD9ListDao icd9ListDao;

    public List<ICD9> findICD9(final ICD9Filter filter) {
        //get elements from icd9_list table
        if (StringUtils.isNotBlank(filter.getListCode()) &&
                StringUtils.isBlank(filter.getName()) &&
                StringUtils.isBlank(filter.getCode())) {
            List<String> codes = icd9ListDao.getICD9Codes(filter.getListCode());
            if (CollectionUtils.isNotEmpty(codes)) {
                List<ICD9> icd9List = new ArrayList<ICD9>();
                for (String code : codes) {
                    icd9List.add(icd9Dao.getByCode(code));
                }
                return icd9List;
            }
        }
        return icd9Dao.getList(filter);
    }
}
