package pl.edu.agh.service;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import pl.edu.agh.dao.ICD9ListDao;
import pl.edu.agh.dao.JGPDao;
import pl.edu.agh.dao.JGPParameterDao;
import pl.edu.agh.dao.JGPValueDao;
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

    @Autowired
    private JGPValueDao jgpValueDao;

    @Autowired
    private ICD9ListDao icd9ListDao;

    public List<JGP> findJGP(final JGPFilter filter) {
        return jgpDao.getList(filter);
    }

    public List<JGPResult> group(Episode episode) {
        Assert.notEmpty(episode.getStays(), "stays cannot be empty!");

        //zakladam ze dane epizodu zostana rozliczone za pomoca wyznaczanie jpg
        //na razie nie rozliczam na podstawie dodatkowego katalogu świadczeń 1b,1c 1e

        List<ICD9Wrapper> allProcedures = filterProcedures(episode.getStays());
        if (CollectionUtils.isNotEmpty(allProcedures)) {
            //procedury wystepuja
            List<ICD9Wrapper> range0Procedures = filterProcedures(allProcedures, RangeCondition.RANGE_0);
            if (CollectionUtils.isNotEmpty(range0Procedures)) {
                //wystapila porcedura z ranga > 0
                List<ICD9Wrapper> range2Procedures = filterProcedures(range0Procedures, RangeCondition.RANGE_2);
                if (CollectionUtils.isNotEmpty(range2Procedures)) {
                    // wystapila procedura z ranga > 2
                    // ustalanie pobytow dla procedur > 2
                    List<Stay> stays = filterStays(episode, range2Procedures);
                    for (Stay stay : stays) {
                        //ustalanie listy procedur pobytu
                        List<ICD9Wrapper> staysProcedures = stay.getProcedures();
                        //okreslenie jgp dla procedury
                        for (ICD9Wrapper procedure : staysProcedures) {
                            List<JGPParameter> parameters = jgpParameterDao.getByProcedure(procedure.getIcd9());
                            if (CollectionUtils.isNotEmpty(parameters)) {
                                List<JGPResultValue> selectedJGP = new ArrayList<JGPResultValue>();
                                //badanie warunkow JGP
                                for (JGPParameter parameter : parameters) {
                                    if (checkParameters(stay, parameter)) {
                                        JGP jgp = parameter.getJgp();
                                        Double value = jgpValueDao.getByJGP(jgp).getValue(episode.getHospitalType());
                                        JGPResultValue resultValue = new JGPResultValue();
                                        resultValue.setJgp(jgp);
                                        resultValue.setValue(value);
                                        selectedJGP.add(resultValue);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // nie wystapia procedura z ranga > 2
                }
            } else {
                //nie wystapila procedura z ranga > 0
            }
        } else {
            //procedury nie wystepuja
        }

//        List<ICD9Wrapper> importantICD9 = importantICD9Codes(hospitalization);
//
//        if(CollectionUtils.isNotEmpty(importantICD9)) {
//            //wystepuje znaczaca procedura
//            importantICD9.get(0) ;
//            List<JGPParameter> parameters = jgpParameterDao.getByProcedure(importantICD9.get(0).getIcd9());
//            System.out.println(parameters.size());
//        } else {
//            //BRAK znaczacej procedury
//        }
        return null;
    }

    private boolean checkParameters(Stay stay, JGPParameter parameter) {
        //todo here exclude conditions

        //TODO conditions checkers
        Condition condition = parameter.getCondition();
        if(Condition.A.equals(condition)) {
            return CollectionUtils.size(stay.getRecognitions()) == 1 || CollectionUtils.size(stay.getProcedures()) == 1;
        } else if(Condition.B.equals(condition)) {
            boolean range2Cond = hasRange(stay.getProcedures(), RangeCondition.RANGE_2);
            boolean timeCond = stay.getHospitalTime() < 2;
            boolean ageCond = checkAgeLimit(stay, parameter.getAgeLimit());
            return range2Cond && timeCond && ageCond;
        } else if(Condition.C.equals(condition)) {
            return CollectionUtils.size(stay.getRecognitions()) == 2 && CollectionUtils.size(stay.getProcedures()) == 2;
        } else if(Condition.D.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision1procedures2 = CollectionUtils.size(recognitions) == 1 && CollectionUtils.size(procedures) == 2;
            boolean sameLists = false;
            if(recognision1procedures2) {
                List<ICD9List> icd9lists1 = icd9ListDao.getListCodes(procedures.get(0).getIcd9());
                List<ICD9List> icd9lists2 = icd9ListDao.getListCodes(procedures.get(1).getIcd9());
                for(ICD9List list1 : icd9lists1) {
                    for(ICD9List list2 : icd9lists2){
                        if(list1.getListCode().equals(list2.getListCode())) {
                            sameLists = true;
                            break;
                        }
                    }
                    if(sameLists) {
                        break;
                    }
                }
            }
            return recognision1procedures2 && sameLists;
        } else if(Condition.E.equals(condition)) {
            boolean recognision1procedures1 = CollectionUtils.size(stay.getRecognitions()) == 1 || CollectionUtils.size(stay.getProcedures()) == 1;
            boolean ageCond = checkAgeLimit(stay, parameter.getAgeLimit());
            boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit());
            return recognision1procedures1 && ageCond && hospCond;
        } else if(Condition.F.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            boolean procedures2 = CollectionUtils.size(procedures) == 2;
            boolean elseLists = false;
            if(procedures2) {
                List<ICD9List> icd9lists1 = icd9ListDao.getListCodes(procedures.get(0).getIcd9());
                List<ICD9List> icd9lists2 = icd9ListDao.getListCodes(procedures.get(1).getIcd9());
                boolean sameLists = false;
                for(ICD9List list1 : icd9lists1) {
                    for(ICD9List list2 : icd9lists2){
                        if(list1.getListCode().equals(list2.getListCode())) {
                            sameLists = true;
                            break;
                        }
                    }
                    if(sameLists) {
                        break;
                    }
                }
                elseLists = !sameLists;
            }
            return procedures2 && elseLists;
        } else if(Condition.G.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision1procedures2 = CollectionUtils.size(recognitions) == 1 && CollectionUtils.size(procedures) == 2;
            boolean ageCond = checkAgeLimit(stay, parameter.getAgeLimit());
            boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit());
            return recognision1procedures2 && ageCond && hospCond;
        } else if(Condition.H.equals(condition)) {

        }
        throw new IllegalStateException("not implemented condition: " + condition);
    }

    private boolean checkAgeLimit(Stay stay, AgeLimit ageLimit) {
        if(ageLimit != null) {
            int age = stay.getEpisode().age(ageLimit.getTimeUnit());
            return ageLimit.test(age);
        }
        return true;
    }

    private boolean checkHospitalLimit(Stay stay, HospitalLimit hospLimit) {
        if (hospLimit != null) {
            int time = stay.getEpisode().hospital(hospLimit.getTimeUnit());
            return hospLimit.test(time);
        }
        return true;
    }

    private List<Stay> filterStays(Episode episode, List<ICD9Wrapper> filtredProcedures) {
        List<Stay> result   = new ArrayList<Stay>();
        List<Stay> allStays = episode.getStays();
        for(Stay stay : allStays) {
            for(ICD9Wrapper procedure : stay.getProcedures()) {
                if(filtredProcedures.contains(procedure)) {
                    result.add(stay);
                    break;
                }
            }
        }
        return result;
    }

    /**
     * Sprawdzanie wystepowania procedur medycznych w danych epizodu
     */
    private List<ICD9Wrapper> filterProcedures(List<Stay> stays) {
        List<ICD9Wrapper> procedures = new ArrayList<ICD9Wrapper>();
        for(Stay stay : stays) {
            procedures.addAll(stay.getProcedures());
        }
        return procedures;
    }

    /**
     * filter procedures with greater then range condition
     */
    private List<ICD9Wrapper> filterProcedures(List<ICD9Wrapper> procedures, RangeCondition rangeCondition) {
        List<ICD9Wrapper> filtredProcedures = new ArrayList<ICD9Wrapper>();
        for(ICD9Wrapper icd9Wrapper : procedures) {
            if(rangeCondition.greaterThen(icd9Wrapper.getIcd9().getRange())) {
                filtredProcedures.add(icd9Wrapper);
            }
        }
        return filtredProcedures;
    }

    private boolean hasRange(List<ICD9Wrapper> procedures, RangeCondition rangeCondition) {
        for(ICD9Wrapper icd9Wrapper : procedures) {
            if(rangeCondition.equalsTo(icd9Wrapper.getIcd9().getRange())) {
                return true;
            }
        }
        return false;
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
