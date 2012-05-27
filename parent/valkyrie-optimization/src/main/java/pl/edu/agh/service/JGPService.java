package pl.edu.agh.service;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.dao.JGPDao;
import pl.edu.agh.dao.JGPParameterDao;
import pl.edu.agh.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class JGPService {
    @Autowired
    private JGPDao jgpDao;

    @Autowired
    private JGPParameterDao jgpParameterDao;

    public List<JGP> findJGP(final JGPFilter filter) {
        return jgpDao.getList(filter);
    }

    public JGPResult group(Hospitalization hospitalization) {
        if (CollectionUtils.isEmpty(hospitalization.getVisits())) {
            throw new IllegalArgumentException("visits cannot be empty!");
        }
        List<ICD9Wrapper> importantICD9 = importantICD9Codes(hospitalization);

        if(CollectionUtils.isNotEmpty(importantICD9)) {
            //wystepuje znaczaca procedura
            importantICD9.get(0) ;
            List<JGPParameter> parameters = jgpParameterDao.getByProcedure(importantICD9.get(0).getIcd9());
            System.out.println(parameters.size());
        } else {
            //BRAK znaczacej procedury
        }
        return null;
    }

    /**
     * grupy zabiegowa - procedura znaczaca itp!
     */
    private List<ICD9Wrapper> importantICD9Codes(Hospitalization hospitalization) {
        List<ICD9Wrapper> importantsICD9codes = new ArrayList<ICD9Wrapper>();
        for(Visit visit : hospitalization.getVisits()) {
            for(ICD9Wrapper icd9Wrapper : visit.getProcedures()) {
                if(icd9Wrapper.getIcd9().getRange() > 2) {
                    importantsICD9codes.add(icd9Wrapper);
                }
            }
        }
        return importantsICD9codes;
    }
}
