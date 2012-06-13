package pl.edu.agh.service.condition;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.dao.ICD10ListDao;
import pl.edu.agh.dao.ICD9ListDao;
import pl.edu.agh.domain.*;
import pl.edu.agh.domain.condition.ICDCondition;
import pl.edu.agh.domain.condition.RangeCondition;
import pl.edu.agh.domain.reason.*;

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
    protected boolean checkRangeEqualsTo(List<ICD9Wrapper> procedures, RangeCondition rangeCondition, List<Reason> reasons) {
        for(ICD9Wrapper icd9Wrapper : procedures) {
            if(rangeCondition.equalsTo(icd9Wrapper.getIcd9().getRange())) {
                return true;
            }
        }
        reasons.add(new RangeReason(RangeCondition.RANGE_2, condition()));
        return false;
    }

    protected boolean checkProceduresSize(List<ICD9Wrapper> procedures, int size, List<Reason> reasons) {
        boolean result = size(procedures) >= size;
        if (!result) {
            reasons.add(new ICDSizeReason(size, ListType.ICD9, condition()));
        }
        return result;
    }

    protected boolean checkRecognitionsSize(List<ICD10Wrapper> recognitions, int size, List<Reason> reasons) {
        boolean result = size(recognitions) >= size;
        if (!result) {
            reasons.add(new ICDSizeReason(size, ListType.ICD10, condition()));
        }
        return result;
    }

    /**
     * check if exist recognition with list code
     */
    protected boolean checkExistRecognition(List<ICD10Wrapper> recognitions, String listCode, ICDCondition icdCondition, List<Reason> reasons) {
        if (!ListType.ICD10.equals(icdCondition.listType())) {
            throw new IllegalArgumentException("icd condition must have list type: " + ListType.ICD10);
        }
        if (StringUtils.isBlank(listCode)) {
            return false;
        }
        for (ICD10Wrapper icd10 : recognitions) {
            List<String> listCodes = icd10ListDao.getListCodes(icd10.getIcd10());
            if (listCodes.contains(listCode)) {
                return true;
            }
        }
        reasons.add(new ICDReason(listCode, icdCondition, condition()));
        return false;
    }

    /**
     * check if exist procedure with list code
     */
    protected boolean checkExistProcedure(List<ICD9Wrapper> procedures, String listCode, ICDCondition icdCondition, List<Reason> reasons) {
        if (!ListType.ICD9.equals(icdCondition.listType())) {
            throw new IllegalArgumentException("icd condition must have list type: " + ListType.ICD9);
        }
        if (StringUtils.isBlank(listCode)) {
            return false;
        }
        for (ICD9Wrapper icd9 : procedures) {
            List<String> listCodes = icd9ListDao.getListCodes(icd9.getIcd9());
            if (listCodes.contains(listCode)) {
                return true;
            }
        }
        reasons.add(new ICDReason(listCode, icdCondition, condition()));
        return false;
    }

    /**
     * check if 2 procedures are on same/else lists
     */
    protected boolean checkSameLists(ICD9Wrapper procedure1, ICD9Wrapper procedure2, boolean isSameList, List<Reason> reasons) {
        List<String> listCodes = icd9ListDao.getListCodes(procedure1.getIcd9(), procedure2.getIcd9());
        boolean result = isSameList ? CollectionUtils.isNotEmpty(listCodes) : CollectionUtils.isEmpty(listCodes);
        if (!result) {
            reasons.add(new ICDReason(procedure1.getIcd9().getCode() + " " + procedure2.getIcd9().getCode(),
                                       isSameList ? ICDCondition.SAME_ICD9 : ICDCondition.ELSE_ICD9,
                                        condition()));
        }
        return result;
    }

    /**
     * check if 2 recognitions are on same/else lists
     */
    protected boolean checkSameLists(ICD10Wrapper recognition1, ICD10Wrapper recognition2, boolean isSameList, List<Reason> reasons) {
        List<String> listCodes = icd10ListDao.getListCodes(recognition1.getIcd10(), recognition2.getIcd10());
        boolean result = isSameList ? CollectionUtils.isNotEmpty(listCodes) : CollectionUtils.isEmpty(listCodes);
        if (!result) {
            reasons.add(new ICDReason(recognition1.getIcd10().getCode() + " " + recognition2.getIcd10().getCode(),
                    isSameList ? ICDCondition.SAME_ICD10 : ICDCondition.ELSE_ICD10,
                    condition()));
        }
        return result;
    }

    protected boolean checkAgeLimit(Stay stay, AgeLimit ageLimit, List<Reason> reasons) {
        if(ageLimit != null) {
            int age = stay.getEpisode().age(ageLimit.getTimeUnit());
            boolean result = ageLimit.test(age);
            if (!result) {
                reasons.add(new AgeReason(ageLimit, condition()));
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
                reasons.add(new HospitalReason(hospLimit, condition()));
            }
            return result;
        }
        return true;
    }
}
