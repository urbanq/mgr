package pl.edu.agh.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import pl.edu.agh.dao.*;
import pl.edu.agh.domain.*;
import pl.edu.agh.service.condition.AbstractChecker;
import pl.edu.agh.domain.condition.RangeCondition;
import pl.edu.agh.domain.reason.*;
import pl.edu.agh.domain.reason.Reason;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

/**
 * User: mateusz
 * Date: 14.05.12
 */
@Service
public class JGPService {
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private JGPDao jgpDao;
    @Autowired
    private JGPParameterDao jgpParameterDao;
    @Autowired
    private JGPValueDao jgpValueDao;
    @Autowired
    private ICD9ListDao icd9ListDao;
    @Autowired
    private ICD10ListDao icd10ListDao;
    @Autowired
    private EnumMap<Condition, AbstractChecker> conditionsMap;


    public List<JGP> findJGP(final JGPFilter filter) {
        return jgpDao.getList(filter);
    }

    /**
     * nie rozliczam na podstawie dodatkowego katalogu świadczeń 1b,1c 1e
     * tylko glowna galaz parametry jgp
    */
    public JGPGroupResult group(Episode episode) {
        Assert.notEmpty(episode.getStays(), "stays cannot be empty!");

        List<Stay> stays = episode.getStays();
        List<ICD9Wrapper> procedures = procedures(stays);

        JGPGroupResult jgpGroupResult;
        if(CollectionUtils.isNotEmpty(procedures) &&
            (checkRangeGreaterThen(procedures, RangeCondition.RANGE_2) ||
            (checkRangeEqualsTo(procedures, RangeCondition.RANGE_2) && episode.hospitalTime(TimeUnit.DAY) < 2))) {
            jgpGroupResult = doByProcedures(episode);
        } else {
            jgpGroupResult = doByRecognitions(episode);
        }
        if(CollectionUtils.isNotEmpty(jgpGroupResult.accepted())) {
            doManDays(episode, jgpGroupResult.accepted());
        }
        return jgpGroupResult;
    }

    private JGPGroupResult doByProcedures(Episode episode) {
        JGPGroupResult jgpGroupResult = new JGPGroupResult();
        for (Stay stay : episode.getStays()) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            for (ICD9Wrapper procedure : procedures) {
                List<JGPParameter> parameters = jgpParameterDao.getByProcedure(procedure.getIcd9());
                resolveResultsByJGP(stay, parameters, jgpGroupResult);
            }

        }
        return jgpGroupResult;
    }

    private JGPGroupResult doByRecognitions(Episode episode) {
        JGPGroupResult jgpGroupResult = new JGPGroupResult();
        for (Stay stay : episode.getStays()) {
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            for (ICD10Wrapper recognition : recognitions) {
                List<JGPParameter> parameters = jgpParameterDao.getByRecognition(recognition.getIcd10());
                resolveResultsByJGP(stay, parameters, jgpGroupResult);
            }
        }
        return jgpGroupResult;
    }

    private void resolveResultsByJGP(Stay stay, List<JGPParameter> parameters, JGPGroupResult jgpGroupResult) {
        if (CollectionUtils.isNotEmpty(parameters)) {
            for (JGPParameter parameter : parameters) {
                JGP jgp = parameter.getJgp();
                Double value = jgpValueDao.getByJGP(jgp).getValue(stay.getEpisode().getHospitalType());

                JGPResult jgpResult = new JGPResult();
                jgpResult.setStay(stay);
                jgpResult.setJgp(jgp);
                jgpResult.setValue(value);

                if (checkDirectional(stay, parameter, jgpResult.reasons()) &&
                     checkSex(stay, parameter.getSexLimit(), jgpResult.reasons()) &&
                      checkIncomeMode(stay, parameter.getIncomeMode(), jgpResult.reasons()) &&
                       checkOutcomeMode(stay, parameter.getOutcomeMode(), jgpResult.reasons()) &&
                        checkDepartment(stay, parameter.getJgp(), jgpResult.reasons()) &&
                         checkNegativeICDCode(stay, parameter, jgpResult.reasons())) {
                    jgpGroupResult.accepted().add(jgpResult);
                } else {
                    jgpGroupResult.notAccepted().add(jgpResult);
                }
            }
            Collections.sort(jgpGroupResult.accepted());
            Collections.sort(jgpGroupResult.notAccepted());
        }
    }

    private void doManDays(Episode episode, List<JGPResult> jgpResultList) {
        for (JGPResult jgpResult : jgpResultList) {
            JGPHospital hospital = jgpDao.getHospital(jgpResult.getJgp());
            if (hospital != null) {
                if (episode.hospitalTime(TimeUnit.DAY) < 2) {
                    if (hospital.getUnderValue() > 0.0) {
                        jgpResult.setValue(hospital.getUnderValue());
                    }
                } else {
                    if (hospital.getDays() > 0 &&
                        episode.hospitalTime(TimeUnit.DAY) > hospital.getDays() &&
                        hospital.getOverValue() > 0.0) {
                        //log
                        jgpResult.setValue(hospital.getOverValue());
                    }
                }
            }
        }
    }

    private boolean checkDirectional(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        Condition condition = parameter.getCondition();
        AbstractChecker checker = conditionsMap.get(condition);
        Assert.notNull(checker, "not implemented checker for condition: " + condition);
        return checker.checkCondition(stay, parameter, reasons);
    }

    private boolean checkSex(Stay stay, Sex sex, List<Reason> reasons) {
        if(sex != null) {
            boolean result = sex.equals(stay.getEpisode().getSex());
            if (!result) {
                reasons.add(new SexReason(sex));
            }
            return result;
        }
        return true;
    }

    private boolean checkIncomeMode(Stay stay, IncomeMode incomeMode, List<Reason> reasons) {
        if(incomeMode != null) {
            boolean result = incomeMode.equals(stay.getEpisode().getIncomeMode());
            if (!result) {
                reasons.add(new IncomeModeReason(incomeMode));
            }
            return result;
        }
        return true;
    }

    private boolean checkOutcomeMode(Stay stay, OutcomeMode outcomeMode, List<Reason> reasons) {
        if(outcomeMode != null) {
            boolean result = outcomeMode.equals(stay.getEpisode().getOutcomeMode());
            if (!result) {
                reasons.add(new OutcomeModeReason(outcomeMode));
            }
            return result;
        }
        return true;
    }

    private boolean checkDepartment(Stay stay, JGP jgp, List<Reason> reasons) {
        if (stay.getDepartment() != null && !"111".equals(stay.getDepartment().getId())) {
            List<Department> departments = departmentDao.getByJGP(jgp);
            boolean result = departments.contains(stay.getDepartment());
            if (!result) {
                reasons.add(new DepartmentsReason(departments));
            }
            return result;
        }
        return true;
    }

    /**
     * create all procedures list from stays list
     */
    private List<ICD9Wrapper> procedures(List<Stay> stays) {
        List<ICD9Wrapper> procedures = new ArrayList<ICD9Wrapper>();
        for(Stay stay : stays) {
            procedures.addAll(stay.getProcedures());
        }
        return procedures;
    }

    private boolean checkRangeGreaterThen(List<ICD9Wrapper> procedures, RangeCondition rangeCondition) {
        for(ICD9Wrapper icd9Wrapper : procedures) {
            if(rangeCondition.greaterThen(icd9Wrapper.getIcd9().getRange())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkRangeEqualsTo(List<ICD9Wrapper> procedures, RangeCondition rangeCondition) {
        for(ICD9Wrapper icd9Wrapper : procedures) {
            if(rangeCondition.equalsTo(icd9Wrapper.getIcd9().getRange())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNegativeICDCode(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        if (StringUtils.isNotBlank(parameter.getNegativeICD10ListCode())) {
            return checkNegativeRecognition(stay.getRecognitions(), parameter.getNegativeICD10ListCode(), reasons);
        }
        if (StringUtils.isNotBlank(parameter.getNegativeICD9ListCode())) {
            return checkNegativeProcedure(stay.getProcedures(), parameter.getNegativeICD9ListCode(), reasons);
        }
        return true;
    }

    private boolean checkNegativeProcedure(List<ICD9Wrapper> procedures, String listCode, List<Reason> reasons) {
        if (checkExistProcedure(procedures, listCode)) {
            reasons.add(new NegativeICDReason(listCode, ListType.ICD9));
            return false;
        }
        return true;
    }

    private boolean checkNegativeRecognition(List<ICD10Wrapper> recognitions, String listCode, List<Reason> reasons) {
        if (checkExistRecognition(recognitions, listCode)) {
            reasons.add(new NegativeICDReason(listCode, ListType.ICD10));
            return false;
        }
        return true;
    }

    /**
     * check if exist recognition with list code
     */
    private boolean checkExistRecognition(List<ICD10Wrapper> recognitions, String listCode) {
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
    private boolean checkExistProcedure(List<ICD9Wrapper> procedures, String listCode) {
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
}
