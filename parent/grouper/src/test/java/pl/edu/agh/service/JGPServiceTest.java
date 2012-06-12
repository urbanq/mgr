package pl.edu.agh.service;

import junit.framework.Assert;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.AbstractTest;
import pl.edu.agh.dao.DepartmentDao;
import pl.edu.agh.dao.ICD10Dao;
import pl.edu.agh.dao.ICD9Dao;
import pl.edu.agh.domain.*;
import pl.edu.agh.domain.condition.ICDCondition;
import pl.edu.agh.domain.reason.HospitalReason;
import pl.edu.agh.domain.reason.ICDReason;
import pl.edu.agh.domain.reason.ICDSizeReason;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class JGPServiceTest extends AbstractTest {
    @Autowired private JGPService jgpService;
    @Autowired private ICD9Dao icd9Dao;
    @Autowired private ICD10Dao icd10Dao;
    @Autowired private DepartmentDao departmentDao;

    private static final DateTime INCOME_DATE = new DateTime(2012,6,1, 10,30);

    @Test
    public void testGrouperA() {
        Episode episode = createTestEpisode(new String[]{"A01.0"}, null, 7, 18);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(1, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(31.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("F47", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(41, result.notAccepted().size());
        JGPResult notAcceptedJGP1 = result.notAccepted().get(0);
        Assert.assertEquals(235.0, notAcceptedJGP1.getValue(), 0.0);
        Assert.assertEquals("E78", notAcceptedJGP1.getJgp().getCode());
        JGPResult notAcceptedJGP2 = result.notAccepted().get(2);
        Assert.assertEquals(110.0, notAcceptedJGP2.getValue(), 0.0);
        Assert.assertEquals("A30", notAcceptedJGP2.getJgp().getCode());
    }

    @Test
    public void testGrouperB() {
        Episode episode = createTestEpisode(null, new String[]{"03.921"}, 1, 18);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(1, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(13.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("A26", acceptedJGP.getJgp().getCode());
    }

    @Test
    public void testGrouperC() {
        Episode episode = createTestEpisode(new String[]{"I10", "Q61.0"}, new String[]{"89.17", "92.09"}, 4, 18);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(4, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(80.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("E86", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(1, result.notAccepted().size());
        JGPResult notAcceptedJGP = result.notAccepted().get(0);
        Assert.assertEquals(25.0, notAcceptedJGP.getValue(), 0.0);
        Assert.assertEquals("D36", notAcceptedJGP.getJgp().getCode());
        ICDReason icdReason = notAcceptedJGP.reasons(ICDReason.class).get(0);
        Assert.assertEquals(ICDCondition.MAIN_ICD10, icdReason.icdCondition());
        Assert.assertEquals("D36", icdReason.required());
    }

    @Test
    public void testGrouperD() {
        Episode episode = createTestEpisode(new String[]{"G45.0"}, new String[]{"88.722", "89.541"}, 7, 18);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(2, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(74.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("A47", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(0, result.notAccepted().size());
    }

    @Test
    public void testGrouperE() {
        Episode episode = createTestEpisode(new String[]{"G08"}, new String[]{"88.714"}, 7, 75);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(1, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(77.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("A49", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(1, result.notAccepted().size());
        JGPResult notAcceptedJGP = result.notAccepted().get(0);
        Assert.assertEquals(162.0, notAcceptedJGP.getValue(), 0.0);
        Assert.assertEquals("A48", notAcceptedJGP.getJgp().getCode());
        HospitalReason hospReason = notAcceptedJGP.reasons(HospitalReason.class).get(0);
        Assert.assertEquals(7, hospReason.required().getOver());
        Assert.assertEquals(TimeUnit.DAY, hospReason.required().getTimeUnit());
    }

    @Test
    public void testGrouperF() {
        Episode episode = createTestEpisode(null, new String[]{"11.63", "11.651"}, 0, 18);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(1, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(172.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("B04", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(2, result.notAccepted().size());
        JGPResult notAcceptedJGP = result.notAccepted().get(0);
        Assert.assertEquals(136.0, notAcceptedJGP.getValue(), 0.0);
        Assert.assertEquals("B05", notAcceptedJGP.getJgp().getCode());
        ICDSizeReason sizeReason = notAcceptedJGP.reasons(ICDSizeReason.class).get(0);
        Assert.assertEquals(1, sizeReason.required().intValue());
        Assert.assertEquals(ListType.ICD10, sizeReason.listType());
    }

    @Test
    public void testGrouperG() {
        Episode episode = createTestEpisode(new String[]{"I63.9"}, new String[]{"96.75", "95.121"}, 8, 18);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(1, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(162.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("A48", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(1, result.notAccepted().size());
        JGPResult notAcceptedJGP = result.notAccepted().get(0);
        Assert.assertEquals(77.0, notAcceptedJGP.getValue(), 0.0);
        Assert.assertEquals("A49", notAcceptedJGP.getJgp().getCode());
        ICDSizeReason sizeReason = notAcceptedJGP.reasons(ICDSizeReason.class).get(0);
        Assert.assertEquals(1, sizeReason.required().intValue());
        Assert.assertEquals(ListType.ICD9, sizeReason.listType());
    }

    @Test
    public void testGrouperH() {
        Episode episode = createTestEpisode(null, new String[]{"00.661", "00.47"}, 8, 18);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(1, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(170.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("E24", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(8, result.notAccepted().size());
        JGPResult notAcceptedJGP = result.notAccepted().get(0);
        Assert.assertEquals(295.0, notAcceptedJGP.getValue(), 0.0);
        Assert.assertEquals("E23", notAcceptedJGP.getJgp().getCode());
        ICDReason icdReason = notAcceptedJGP.reasons(ICDReason.class).get(0);
        Assert.assertEquals(ICDCondition.FIRST_ICD9, icdReason.icdCondition());
        Assert.assertEquals("E4", icdReason.required());
    }

    @Test
    public void testGrouperI() {
        Episode episode = createTestEpisode(null, new String[]{"00.611", "00.45", "00.42"}, 8, 18);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(1, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(180.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("Q43", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(8, result.notAccepted().size());
        JGPResult notAcceptedJGP = result.notAccepted().get(0);
        Assert.assertEquals(289.0, notAcceptedJGP.getValue(), 0.0);
        Assert.assertEquals("E11", notAcceptedJGP.getJgp().getCode());
        ICDReason icdReason = notAcceptedJGP.reasons(ICDReason.class).get(0);
        Assert.assertEquals(ICDCondition.MAIN_ICD10, icdReason.icdCondition());
        Assert.assertEquals("E14", icdReason.required());
    }

    @Test
    public void testGrouperJ() {
        Episode episode = createTestEpisode(new String[]{"G24.0"}, new String[]{"02.931", "03.93", "00.36"}, 8, 18);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(2, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(940.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("A03", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(3, result.notAccepted().size());
        JGPResult notAcceptedJGP = result.notAccepted().get(0);
        Assert.assertEquals(940.0, notAcceptedJGP.getValue(), 0.0);
        Assert.assertEquals("A03", notAcceptedJGP.getJgp().getCode());
        ICDReason icdReason = notAcceptedJGP.reasons(ICDReason.class).get(0);
        Assert.assertEquals(ICDCondition.FIRST_ICD10, icdReason.icdCondition());
        Assert.assertEquals("A5", icdReason.required());
    }

    @Test
    public void testGrouperK() {
        Episode episode = createTestEpisode(new String[]{"T31.2", "T20.0"}, new String[]{"86.601"}, 11, 18);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(2, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(844.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("J11", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(14, result.notAccepted().size());
        JGPResult notAcceptedJGP = result.notAccepted().get(0);
        Assert.assertEquals(844.0, notAcceptedJGP.getValue(), 0.0);
        Assert.assertEquals("J11", notAcceptedJGP.getJgp().getCode());
        ICDSizeReason sizeReason = notAcceptedJGP.reasons(ICDSizeReason.class).get(0);
        Assert.assertEquals(1, sizeReason.required().intValue());
        Assert.assertEquals(ListType.ICD10, sizeReason.listType());
    }

    @Test
    public void testGrouperL() {
        Episode episode = createTestEpisode(new String[]{"P24.0", "B25.8", "G00.0"}, new String[]{"42.85", "34.041"}, new TimeValue(31, TimeUnit.DAY), new TimeValue(12, TimeUnit.WEEK));
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(4, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(18.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("N21", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(10, result.notAccepted().size());
        JGPResult notAcceptedJGP = result.notAccepted().get(0);
        Assert.assertEquals(1000.0, notAcceptedJGP.getValue(), 0.0);
        Assert.assertEquals("N21", notAcceptedJGP.getJgp().getCode());
        ICDReason icdReason = notAcceptedJGP.reasons(ICDReason.class).get(0);
        Assert.assertEquals(ICDCondition.MAIN_ICD10, icdReason.icdCondition());
        Assert.assertEquals("N7A", icdReason.required());
    }

    @Test
    public void testGrouperM() {
        Episode episode = createTestEpisode(new String[]{"O10.1", "O80.0"}, new String[]{"72.1", "89.393", "75.32"}, 14, 18);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(1, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(6.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("N03", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(5, result.notAccepted().size());
        JGPResult notAcceptedJGP = result.notAccepted().get(0);
        Assert.assertEquals(164.0, notAcceptedJGP.getValue(), 0.0);
        Assert.assertEquals("N11", notAcceptedJGP.getJgp().getCode());
        ICDReason icdReason = notAcceptedJGP.reasons(ICDReason.class).get(0);
        Assert.assertEquals(ICDCondition.FIRST_ICD9, icdReason.icdCondition());
        Assert.assertEquals("N5", icdReason.required());
    }

    @Test
    public void testGrouperN() {
        Episode episode = createTestEpisode(new String[]{"S32.1", "G82.0"}, new String[]{"79.293"}, 14, 18);
        //run grouper
        JGPGroupResult result = jgpService.group(episode);
        //test accepted
        Assert.assertEquals(1, result.accepted().size());
        JGPResult acceptedJGP = result.accepted().get(0);
        Assert.assertEquals(361.0, acceptedJGP.getValue(), 0.0);
        Assert.assertEquals("H60", acceptedJGP.getJgp().getCode());
        //test NOT accepted
        Assert.assertEquals(1, result.notAccepted().size());
        JGPResult notAcceptedJGP = result.notAccepted().get(0);
        Assert.assertEquals(260.0, notAcceptedJGP.getValue(), 0.0);
        Assert.assertEquals("H61", notAcceptedJGP.getJgp().getCode());
        ICDSizeReason sizeReason = notAcceptedJGP.reasons(ICDSizeReason.class).get(0);
        Assert.assertEquals(ListType.ICD10, sizeReason.listType());
        Assert.assertEquals(1, sizeReason.required().intValue());
    }

    //helpers
    private Episode createTestEpisode(String[] icd10Codes, String[] icd9Codes, TimeValue hospital, TimeValue age) {
        Episode episode = new Episode();
        switch (age.unit) {
           case YEAR:
               episode.setDateOfBirth(new DateTime().minusYears(age.value).toDate());
               break;
           case WEEK:
               episode.setDateOfBirth(new DateTime().minusWeeks(age.value).toDate());
               break;
           case DAY:
               episode.setDateOfBirth(new DateTime().minusDays(age.value).toDate());
               break;
        }
        episode.setIncomeDate(INCOME_DATE.toDate());
        switch (hospital.unit) {
            case YEAR:
                episode.setOutcomeDate(INCOME_DATE.plusYears(hospital.value).toDate());
                break;
            case WEEK:
                episode.setOutcomeDate(INCOME_DATE.plusWeeks(hospital.value).toDate());
                break;
            case DAY:
                episode.setOutcomeDate(INCOME_DATE.plusDays(hospital.value).toDate());
                break;
        }
        Stay stay = new Stay();
        episode.getStays().add(stay);
        stay.setIncomeDate(INCOME_DATE.toDate());
        switch (hospital.unit) {
            case YEAR:
                stay.setOutcomeDate(INCOME_DATE.plusYears(hospital.value).toDate());
                break;
            case WEEK:
                stay.setOutcomeDate(INCOME_DATE.plusWeeks(hospital.value).toDate());
                break;
            case DAY:
                stay.setOutcomeDate(INCOME_DATE.plusDays(hospital.value).toDate());
                break;
        }
        stay.setEpisode(episode);
        stay.setDepartment(departmentDao.get("111"));
        stay.setService(Service.BASE_HOSPITAL);
        if (icd10Codes != null && icd10Codes.length > 0) {
            for (String icd10Code : icd10Codes) {
                ICD10Wrapper icd10 = new ICD10Wrapper();
                icd10.setIcd10(icd10Dao.getByCode(icd10Code));
                stay.getRecognitions().add(icd10);
            }
        }
        if(icd9Codes != null && icd9Codes.length > 0) {
            for (String icd9Code : icd9Codes) {
                ICD9Wrapper icd9 = new ICD9Wrapper();
                icd9.setIcd9(icd9Dao.getByCode(icd9Code));
                icd9.setProcedureDate(INCOME_DATE.toDate());
                stay.getProcedures().add(icd9);
            }
        }
        return episode;
    }

    private Episode createTestEpisode(String[] icd10Codes, String[] icd9Codes, int hospitalDays, int ageYears) {
        return createTestEpisode(icd10Codes, icd9Codes, new TimeValue(hospitalDays, TimeUnit.DAY), new TimeValue(ageYears, TimeUnit.YEAR));
    }

    private class TimeValue {
        private int value;
        private TimeUnit unit;

        private TimeValue(int value, TimeUnit unit) {
            this.value = value;
            this.unit = unit;
        }

        public int value() {
            return value;
        }

        public TimeUnit unit() {
            return unit;
        }
    }
}
