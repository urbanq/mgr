package pl.edu.agh.service;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import pl.edu.agh.dao.*;
import pl.edu.agh.domain.*;

import java.util.*;

import static org.apache.commons.collections.CollectionUtils.size;
import static pl.edu.agh.domain.JGPParameter.ICDCondition;

/**
 * User: mateusz
 * Date: 14.05.12
 */
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
                resolveResultsByJGP(episode, stay, parameters, jgpGroupResult);
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
                resolveResultsByJGP(episode, stay, parameters, jgpGroupResult);
            }
        }
        return jgpGroupResult;
    }

    private void resolveResultsByJGP(Episode episode, Stay stay, List<JGPParameter> parameters, JGPGroupResult jgpGroupResult) {
        if (CollectionUtils.isNotEmpty(parameters)) {
            for (JGPParameter parameter : parameters) {
                JGP jgp = parameter.getJgp();
                Double value = jgpValueDao.getByJGP(jgp).getValue(episode.getHospitalType());

                JGPResult jgpResult = new JGPResult();
                jgpResult.setStay(stay);
                jgpResult.setJgp(jgp);
                jgpResult.setValue(value);

                if (checkDirectionalConditions(stay, parameter, jgpResult.reasons()) &&
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

    private boolean checkDirectionalConditions(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        Condition condition = parameter.getCondition();
        if(Condition.A.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
            boolean proceduresSize = checkProceduresSize(procedures, 1);
            return recognitionsSize || proceduresSize;

        } else if(Condition.B.equals(condition)) {
            boolean range2Equal = checkRangeEqualsTo(procedures, RangeCondition.RANGE_2);
            boolean hospLimit   = checkHospitalLimit(stay, HospitalLimit.under2Days(), reasons);
            boolean ageLimit    = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
            return range2Equal && hospLimit && ageLimit;

        } else if(Condition.C.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 2);
            boolean proceduresSize   = checkProceduresSize(procedures, 2);
            boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
            return recognitionsSize && proceduresSize && mainRecognition && coexistRecognition;

        } else if(Condition.D.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
            boolean proceduresSize   = checkProceduresSize(procedures, 2);
            boolean sameLists = false;
            if(recognitionsSize && proceduresSize) {
                sameLists = checkSameLists(procedures.get(0), procedures.get(1), true);
            }
            boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
            return recognitionsSize && proceduresSize && sameLists && hospLimit;

        } else if(Condition.E.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
            boolean proceduresSize = checkProceduresSize(procedures, 1);
            boolean mainRecognition = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean ageLimit = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
            boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
            return recognitionsSize && proceduresSize && mainRecognition && ageLimit && hospLimit;

        } else if(Condition.F.equals(condition)) {
            boolean proceduresSize = checkProceduresSize(stay.getProcedures(), 2);
            boolean additionalProcedure = checkExistProcedure(stay.getProcedures(), parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);
            boolean elseLists = false;
            if(proceduresSize && additionalProcedure) {
                elseLists = checkSameLists(procedures.get(0), procedures.get(1), false);
            }
            return proceduresSize && additionalProcedure && elseLists;

        } else if(Condition.G.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
            boolean proceduresSize = checkProceduresSize(procedures, 2);
            boolean additionalProcedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);
            boolean mainRecognition = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean ageLimit = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
            boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
            return recognitionsSize && proceduresSize && additionalProcedure && mainRecognition && ageLimit && hospLimit;

        } else if(Condition.H.equals(condition)) {
            boolean proceduresSize = checkProceduresSize(procedures, 2);
            boolean additionalProcedure = checkExistProcedure(stay.getProcedures(), parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);
            return proceduresSize && additionalProcedure;

        } else if(Condition.I.equals(condition)) {
            boolean proceduresSize = checkProceduresSize(procedures, 3);
            boolean additional1Procedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);
            boolean additional2Procedure = checkExistProcedure(procedures, parameter.getSecondICD9ListCode(), ICDCondition.SECOND_ICD9, reasons);
            boolean elseLists = false;
            if(proceduresSize && additional1Procedure && additional2Procedure) {
                elseLists = checkSameLists(procedures.get(0), procedures.get(1), false)
                         && checkSameLists(procedures.get(0), procedures.get(2), false);
            }
            return proceduresSize && additional1Procedure && additional2Procedure && elseLists;

        } else if(Condition.J.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
            boolean proceduresSize = checkProceduresSize(procedures, 3);
            boolean mainRecognition = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean additional1Procedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);
            boolean additional2Procedure = checkExistProcedure(procedures, parameter.getSecondICD9ListCode(), ICDCondition.SECOND_ICD9, reasons);
            boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
            return recognitionsSize && proceduresSize && mainRecognition
                    && additional1Procedure && additional2Procedure && hospLimit;

        } else if(Condition.K.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 2);
            boolean proceduresSize = checkProceduresSize(procedures, 1);
            boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean elseLists = false;
            if(recognitionsSize && proceduresSize && mainRecognition && coexistRecognition) {
                elseLists = checkSameLists(recognitions.get(0), recognitions.get(1), false);
            }
            boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
            return recognitionsSize && proceduresSize && mainRecognition && coexistRecognition && elseLists && hospLimit;

        } else if(Condition.L.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 3);
            boolean proceduresSize = checkProceduresSize(procedures, 2);
            boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean coexist1Recognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
            boolean coexist2Recognition = checkExistRecognition(recognitions, parameter.getSecondICD10ListCode(), ICDCondition.SECOND_ICD10, reasons);
            boolean elseLists = false;
            if(recognitionsSize && proceduresSize && mainRecognition && coexist1Recognition && coexist2Recognition) {
                elseLists = checkSameLists(recognitions.get(0), recognitions.get(1), false)
                         && checkSameLists(recognitions.get(0), recognitions.get(2), false);
            }
            return recognitionsSize && proceduresSize && mainRecognition
                    && coexist1Recognition && coexist2Recognition && elseLists;

        } else if(Condition.M.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 2);
            boolean proceduresSize = checkProceduresSize(procedures, 3);
            boolean additional1Procedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);
            boolean additional2Procedure = checkExistProcedure(procedures, parameter.getSecondICD9ListCode(), ICDCondition.SECOND_ICD9, reasons);
            boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
            boolean elseProceduresLists = false;
            if(proceduresSize && additional1Procedure && additional2Procedure) {
                elseProceduresLists = checkSameLists(procedures.get(0), procedures.get(1), false)
                         && checkSameLists(procedures.get(0), procedures.get(2), false);
            }
            boolean elseRecognitionsLists = false;
            if(recognitionsSize && mainRecognition && coexistRecognition) {
                elseRecognitionsLists = checkSameLists(recognitions.get(0), recognitions.get(1), false);
            }
            boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
            return recognitionsSize && proceduresSize && additional1Procedure
                    && additional2Procedure && mainRecognition && coexistRecognition
                    && elseProceduresLists && elseRecognitionsLists && hospLimit;

        } else if(Condition.N.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 2);
            boolean proceduresSize = checkProceduresSize(procedures, 1);
            boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
            return recognitionsSize && proceduresSize && mainRecognition && coexistRecognition;

        } else if(Condition.O.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 2);
            boolean proceduresSize = checkProceduresSize(procedures, 2);
            boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
            boolean additional1Procedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);
            boolean additional2Procedure = checkExistProcedure(procedures, parameter.getSecondICD9ListCode(), ICDCondition.SECOND_ICD9, reasons);
            boolean elseRecognitionsLists = false;
            if(recognitionsSize && mainRecognition && coexistRecognition) {
                elseRecognitionsLists = checkSameLists(recognitions.get(0), recognitions.get(1), false);
            }
            boolean sameProceduresLists = false;
            if(proceduresSize && additional1Procedure && additional2Procedure) {
                sameProceduresLists = checkSameLists(procedures.get(0), procedures.get(1), true);
            }
            boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
            return recognitionsSize && proceduresSize && mainRecognition
                    && coexistRecognition && additional1Procedure && additional2Procedure
                    && sameProceduresLists && elseRecognitionsLists && hospLimit;

        } else if(Condition.P.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
            boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean ageLimit = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
            return recognitionsSize && mainRecognition && ageLimit;

        } else if(Condition.Q.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
            boolean proceduresSize = checkProceduresSize(procedures, 1);
            boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
            return recognitionsSize && proceduresSize && mainRecognition && hospLimit;

        } else if(Condition.R.equals(condition)) {
            boolean recognitions2Size = checkRecognitionsSize(recognitions, 2);
            boolean recognitions3Size = checkRecognitionsSize(recognitions, 3);
            boolean coexistRecognition = false;
            if(recognitions2Size) {
                coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
            }
            if (recognitions3Size) {
                coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons)
                                  && checkExistRecognition(recognitions, parameter.getSecondICD10ListCode(), ICDCondition.SECOND_ICD10, reasons);
            }
            return coexistRecognition && (recognitions2Size || recognitions3Size);

        } else if(Condition.S.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
            boolean proceduresSize = checkProceduresSize(procedures, 2);
            boolean coexistRecognition  = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
            return recognitionsSize && proceduresSize && coexistRecognition;

        } else if(Condition.T.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
            boolean proceduresSize = checkProceduresSize(procedures, 3);
            boolean coexistRecognition  = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
            return recognitionsSize && proceduresSize && coexistRecognition;

        } else if(Condition.U.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 2);
            boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
            boolean ageLimit = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
            return recognitionsSize && mainRecognition && coexistRecognition && ageLimit;

        } else if(Condition.V.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
            boolean proceduresSize = checkProceduresSize(procedures, 1);
            boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
            boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
            return recognitionsSize && proceduresSize && coexistRecognition && hospLimit;

        } else if(Condition.W.equals(condition)) {
            boolean procedures1Size = checkProceduresSize(procedures, 1);
            boolean recognitions1Size = checkRecognitionsSize(recognitions, 1);
            boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);

            boolean procedures2Size = checkProceduresSize(procedures, 2);
            boolean additionalProcedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);

            return (procedures1Size && recognitions1Size && mainRecognition) ||
                   (procedures2Size && additionalProcedure);

        } else if(Condition.X.equals(condition)) {
            boolean procedures1Size = checkProceduresSize(procedures, 1);
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 2);
            boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
            boolean additionalProcedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);
            boolean ageLimit = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
            boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
            return procedures1Size && recognitionsSize && mainRecognition &&
                    coexistRecognition && additionalProcedure &&
                     ageLimit && hospLimit;

        } else if(Condition.Y.equals(condition)) {
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
            boolean proceduresSize = checkProceduresSize(procedures, 1);
            boolean ageLimit = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
            boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
            return (recognitionsSize || proceduresSize) && ageLimit && hospLimit;

        } else if(Condition.Z.equals(condition)) {
            boolean proceduresSize = checkProceduresSize(procedures, 4);
            boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
            boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
            boolean additional4Procedures = checkSameDateProcedure(procedures, parameter.getFirstICD9ListCode(), parameter.getSecondICD9ListCode());
            return proceduresSize && recognitionsSize && mainRecognition && additional4Procedures;
        }

        throw new IllegalStateException("not implemented condition: " + condition);
    }

    private boolean checkAgeLimit(Stay stay, AgeLimit ageLimit, List<Reason> reasons) {
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

    private boolean checkHospitalLimit(Stay stay, HospitalLimit hospLimit, List<Reason> reasons) {
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

    private boolean checkSex(Stay stay, Sex sex, List<Reason> reasons) {
        if(sex != null) {
            boolean result = sex.equals(stay.getEpisode().getSex());
            if (!result) {
                reasons.add(new Reason(sex));
            }
            return result;
        }
        return true;
    }

    private boolean checkIncomeMode(Stay stay, IncomeMode incomeMode, List<Reason> reasons) {
        if(incomeMode != null) {
            boolean result = incomeMode.equals(stay.getEpisode().getIncomeMode());
            if (!result) {
                reasons.add(new Reason(incomeMode));
            }
            return result;
        }
        return true;
    }

    private boolean checkOutcomeMode(Stay stay, OutcomeMode outcomeMode, List<Reason> reasons) {
        if(outcomeMode != null) {
            boolean result = outcomeMode.equals(stay.getEpisode().getOutcomeMode());
            if (!result) {
                reasons.add(new Reason(outcomeMode));
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
                reasons.add(new Reason(departments));
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

    private boolean checkProceduresSize(List<ICD9Wrapper> procedures, int size) {
        return size(procedures) == size;
    }

    private boolean checkRecognitionsSize(List<ICD10Wrapper> recognitions, int size) {
        return size(recognitions) == size;
    }

    /**
     * check if 2 procedures are on same/else lists
     */
    private boolean checkSameLists(ICD9Wrapper procedure1, ICD9Wrapper procedure2, boolean isSameList) {
        List<String> listCodes = icd9ListDao.getListCodes(procedure1.getIcd9(), procedure2.getIcd9());
        return isSameList ? CollectionUtils.isNotEmpty(listCodes) : CollectionUtils.isEmpty(listCodes);
    }

    /**
     * check if 2 recognitions are on same/else lists
     */
    private boolean checkSameLists(ICD10Wrapper recognition1, ICD10Wrapper recognition2, boolean isSameList) {
        List<String> listCodes = icd10ListDao.getListCodes(recognition1.getIcd10(), recognition2.getIcd10());
        return isSameList ? CollectionUtils.isNotEmpty(listCodes) : CollectionUtils.isEmpty(listCodes);
    }

    private boolean checkExistRecognition(List<ICD10Wrapper> recognitions, String listCode, ICDCondition icdCondition, List<Reason> reasons) {
        reasons.add(new Reason(icdCondition, listCode));
        return checkExistRecognition(recognitions, listCode);
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
            reasons.add(new Reason(ICDCondition.NEGATIVE_ICD9, listCode));
            return false;
        }
        return true;
    }

    private boolean checkNegativeRecognition(List<ICD10Wrapper> recognitions, String listCode, List<Reason> reasons) {
        if (checkExistRecognition(recognitions, listCode)) {
            reasons.add(new Reason(ICDCondition.NEGATIVE_ICD10, listCode));
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
    private boolean checkExistProcedure(List<ICD9Wrapper> procedures, String listCode, ICDCondition icdCondition, List<Reason> reasons) {
        reasons.add(new Reason(icdCondition, listCode));
        return checkExistProcedure(procedures, listCode);
    }

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

    /**
     * check if exist procedure with list code
     */
    private boolean checkSameDateProcedure(List<ICD9Wrapper> procedures, String firstList, String secondList) {
        if (StringUtils.isBlank(firstList) || StringUtils.isBlank(secondList)) {
            return false;
        }
        List<ICD9Wrapper> firstCode = new ArrayList<ICD9Wrapper>();
        List<ICD9Wrapper> secondCode = new ArrayList<ICD9Wrapper>();
        for (ICD9Wrapper icd9 : procedures) {
            if (checkExistProcedure(Arrays.asList(icd9), firstList)) {
                firstCode.add(icd9);
            }
            if (checkExistProcedure(Arrays.asList(icd9), secondList)) {
                secondCode.add(icd9);
            }
        }
        int occured = 0;
        Iterator<ICD9Wrapper> firstIter = firstCode.iterator();
        while (firstIter.hasNext()) {
            ICD9Wrapper firstICD9 = firstIter.next();
            Iterator<ICD9Wrapper> secondIter = secondCode.iterator();
            while (secondIter.hasNext()) {
                ICD9Wrapper secondICD9 = secondIter.next();
                if(DateUtils.isSameDay(firstICD9.getProcedureDate(), secondICD9.getProcedureDate())) {
                    occured++;
                    firstIter.remove();
                    secondIter.remove();
                }
            }
        }
        return occured > 1;
    }

    private enum RangeCondition {
        RANGE_0(0),
        RANGE_2(2);

        private final Integer range;

        private RangeCondition(Integer range) {
            this.range = range;
        }

        public boolean greaterThen(Integer rangeToCheck) {
            return rangeToCheck > range;
        }

        public boolean equalsTo(Integer rangeToCheck) {
            return rangeToCheck == range;
        }
    }
}
