package pl.edu.agh.service;

import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.AbstractTest;
import pl.edu.agh.dao.DepartmentDao;
import pl.edu.agh.dao.ICD10Dao;
import pl.edu.agh.domain.*;

import java.util.Date;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class JGPServiceTest extends AbstractTest {
    @Autowired private JGPService jgpService;
    @Autowired private ICD10Dao icd10Dao;
    @Autowired private DepartmentDao departmentDao;

    private static final Date INCOME_DATE  = new DateTime(2012,6,1, 10,30).toDate();
    private static final Date OUTCOME_DATE = new DateTime(2012,6,8, 14,10).toDate();

    @Test
    public void testGroup() {
        Episode episode = new Episode();
        episode.setIncomeDate(INCOME_DATE);
        episode.setOutcomeDate(OUTCOME_DATE);

        Stay stay = new Stay();
        episode.getStays().add(stay);
        stay.setIncomeDate(INCOME_DATE);
        stay.setOutcomeDate(OUTCOME_DATE);
        stay.setEpisode(episode);
        stay.setDepartment(departmentDao.get("111"));
        ICD10Wrapper icd10 = new ICD10Wrapper();
        icd10.setIcd10(icd10Dao.getByCode("A01.0"));
        stay.setService(Service.BASE_HOSPITAL);
        stay.getRecognitions().add(icd10);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);

        //check accepted results
        Assert.assertEquals(1, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(31.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("F47", acceptedJGP.getJgp().getCode());
    }
}
