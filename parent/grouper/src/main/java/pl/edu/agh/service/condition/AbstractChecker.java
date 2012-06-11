package pl.edu.agh.service.condition;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.dao.ICD10ListDao;
import pl.edu.agh.dao.ICD9ListDao;
import pl.edu.agh.domain.*;

import java.util.List;

import static org.apache.commons.collections.CollectionUtils.size;

/**
 * @author mateusz
 * @date 11.06.12
 */
public abstract class AbstractChecker {
    @Autowired
    private ICD9ListDao icd9ListDao;
    @Autowired
    private ICD10ListDao icd10ListDao;

    private final Condition condition;

    protected AbstractChecker(Condition condition) {
        this.condition = condition;
    }

    public abstract boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons);

    public Condition condition() {
        return condition;
    }

    //helpers
    protected boolean checkRangeGreaterThen(List<ICD9Wrapper> procedures, RangeCondition rangeCondition) {
        for(ICD9Wrapper icd9Wrapper : procedures) {
            if(rangeCondition.greaterThen(icd9Wrapper.getIcd9().getRange())) {
                return true;
            }
        }
        return false;
    }

    protected boolean checkRangeEqualsTo(List<ICD9Wrapper> procedures, RangeCondition rangeCondition) {
        for(ICD9Wrapper icd9Wrapper : procedures) {
            if(rangeCondition.equalsTo(icd9Wrapper.getIcd9().getRange())) {
                return true;
            }
        }
        return false;
    }

    protected boolean checkProceduresSize(List<ICD9Wrapper> procedures, int size) {
        return size(procedures) == size;
    }

    protected boolean checkRecognitionsSize(List<ICD10Wrapper> recognitions, int size) {
        return size(recognitions) == size;
    }

    /**
     * check if exist recognition with list code
     */
    protected boolean checkExistRecognition(List<ICD10Wrapper> recognitions, String listCode) {
        if (StringUtils.isBlank(listCode)) {
            return false;
        }
        for (ICD10Wrapper icd10 : recognitions) {
            List<String> listCodes = icd10ListDao.getListCodes(icd10.getIcd10());
            if (listCodes.contains(listCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if exist procedure with list code
     */
    protected boolean checkExistProcedure(List<ICD9Wrapper> procedures, String listCode) {
        if (StringUtils.isBlank(listCode)) {
            return false;
        }
        for (ICD9Wrapper icd9 : procedures) {
            List<String> listCodes = icd9ListDao.getListCodes(icd9.getIcd9());
            if (listCodes.contains(listCode)) {
                return true;
            }
        }
        return false;
    }

    /**
     * check if 2 procedures are on same/else lists
     */
    protected boolean checkSameLists(ICD9Wrapper procedure1, ICD9Wrapper procedure2, boolean isSameList) {
        List<String> listCodes = icd9ListDao.getListCodes(procedure1.getIcd9(), procedure2.getIcd9());
        return isSameList ? CollectionUtils.isNotEmpty(listCodes) : CollectionUtils.isEmpty(listCodes);
    }

    /**
     * check if 2 recognitions are on same/else lists
     */
    protected boolean checkSameLists(ICD10Wrapper recognition1, ICD10Wrapper recognition2, boolean isSameList) {
        List<String> listCodes = icd10ListDao.getListCodes(recognition1.getIcd10(), recognition2.getIcd10());
        return isSameList ? CollectionUtils.isNotEmpty(listCodes) : CollectionUtils.isEmpty(listCodes);
    }

    protected boolean checkAgeLimit(Stay stay, AgeLimit ageLimit, List<Reason> reasons) {
        if(ageLimit != null) {
            int age = stay.getEpisode().age(ageLimit.getTimeUnit());
            boolean result = ageLimit.test(age);
            if (!result) {
                reasons.add(new Reason(ageLimit));
            }
            return result;
        }
        return true;
    }

    protected boolean checkHospitalLimit(Stay stay, HospitalLimit hospLimit, List<Reason> reasons) {
        if (hospLimit != null) {
            int time = stay.getEpisode().hospitalTime(hospLimit.getTimeUnit());
            boolean result = hospLimit.test(time);
            if (!result) {
                reasons.add(new Reason(hospLimit));
            }
            return result;
        }
        return true;
    }
}
