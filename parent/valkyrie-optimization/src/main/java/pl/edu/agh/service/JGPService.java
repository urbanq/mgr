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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.size;

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
        List<JGPResult> jgpResultList = new ArrayList<JGPResult>();

        //na razie nie rozliczam na podstawie dodatkowego katalogu świadczeń 1b,1c 1e
        //tylko glowna galaz parametry jgp
//        List<Stay> stays = episode.getStays();
        List<ICD9Wrapper> procedures = procedures(episode.getStays());

//        if(CollectionUtils.isNotEmpty(procedures) &&
//            (hasRangeGreaterThen(procedures, RangeCondition.RANGE_2) ||
//            (hasRangeEqualsTo(procedures, RangeCondition.RANGE_2) && episode.hospitalTime(TimeUnit.DAY) < 2))) {
//            doProcedures(episode, jgpResultList);
//        } else {
//            doRecognitions(episode, jgpResultList);
//        }

        if (CollectionUtils.isNotEmpty(procedures)) {
            //procedury wystepuja
            List<ICD9Wrapper> range0Procedures = filterProcedures(procedures, RangeCondition.RANGE_0);
            if (CollectionUtils.isNotEmpty(range0Procedures)) {
                //wystapila porcedura z ranga > 0
                List<ICD9Wrapper> range2Procedures = filterProcedures(range0Procedures, RangeCondition.RANGE_2);
                if (CollectionUtils.isNotEmpty(range2Procedures)) {
                    // wystapila procedura z ranga > 2
                    // ustalanie pobytow dla procedur > 2
                    List<Stay> stays = filterStays(episode, range2Procedures);
                    for (Stay stay : stays) {
                        JGPResult result = new JGPResult();
                        result.setStay(stay);
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
                                        result.getParameters().add(parameter);
                                    }
                                }
                                if(CollectionUtils.isEmpty(selectedJGP)) {
                                    result.setValue(0.0);
                                } else {
                                    JGPResultValue maxValue = Collections.max(selectedJGP, new Comparator<JGPResultValue>() {
                                        @Override
                                        public int compare(JGPResultValue o1, JGPResultValue o2) {
                                            return o1.getValue().compareTo(o2.getValue());
                                        }
                                    });
                                    result.setValue(maxValue.getValue());
                                }
                            }
                        }
                        jgpResultList.add(result);
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
        return jgpResultList;
    }

    private boolean checkParameters(Stay stay, JGPParameter parameter) {
        //todo here exclude conditions

        //TODO conditions checkers
        Condition condition = parameter.getCondition();
        if(Condition.A.equals(condition)) {
            return size(stay.getRecognitions()) == 1 || size(stay.getProcedures()) == 1;
        } else if(Condition.B.equals(condition)) {
            boolean range2Cond = hasRangeEqualsTo(stay.getProcedures(), RangeCondition.RANGE_2);
            boolean timeCond = stay.getHospitalTime() < 2;
            boolean ageCond = checkAgeLimit(stay, parameter.getAgeLimit());
            return range2Cond && timeCond && ageCond;
        } else if(Condition.C.equals(condition)) {
            return size(stay.getRecognitions()) == 2 && size(stay.getProcedures()) == 2;
        } else if(Condition.D.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision1procedures2 = size(recognitions) == 1 && size(procedures) == 2;
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
            boolean recognision1procedures1 = size(stay.getRecognitions()) == 1 || size(stay.getProcedures()) == 1;
            boolean ageCond = checkAgeLimit(stay, parameter.getAgeLimit());
            boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit());
            return recognision1procedures1 && ageCond && hospCond;
        } else if(Condition.F.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            boolean procedures2 = size(procedures) == 2;
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
            boolean recognision1procedures2 = size(recognitions) == 1 && size(procedures) == 2;
            boolean ageCond = checkAgeLimit(stay, parameter.getAgeLimit());
            boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit());
            return recognision1procedures2 && ageCond && hospCond;
        } else if(Condition.H.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            //grupa zdefiniowana procedura podstawowa i druga procedura alternatywnie z jednej z dwoch list dodatkowych
            boolean procedures2 = size(procedures) == 2;
            List<ICD9List> icd9lists2 = icd9ListDao.getListCodes(procedures.get(1).getIcd9());
            //?
            return procedures2;
        } else if(Condition.I.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            boolean procedures3 = size(procedures) == 3;
            boolean elseLists = false;
            if(procedures3) {
                List<ICD9List> icd9lists1 = icd9ListDao.getListCodes(procedures.get(0).getIcd9());
                List<ICD9List> icd9lists2 = icd9ListDao.getListCodes(procedures.get(1).getIcd9());
                List<ICD9List> icd9lists3 = icd9ListDao.getListCodes(procedures.get(2).getIcd9());
                boolean sameLists = false;
                for(ICD9List list2 : icd9lists2) {
                    for(ICD9List list3 : icd9lists3){
                        if(list2.getListCode().equals(list3.getListCode())) {
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
            return procedures3 && elseLists;
        } else if(Condition.J.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision1procedures3 = size(recognitions) == 1 && size(procedures) == 3;
            boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit());
            return recognision1procedures3 && hospCond;
        } else if(Condition.K.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision2procedures1 = size(recognitions) == 2 && size(procedures) == 1;
            //z roznych list dodatkowych */
            boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit());
            return recognision2procedures1 && hospCond;
        } else if(Condition.L.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision3procedures2 = size(recognitions) == 3 && size(procedures) == 2;
            //grupa zdeifiniowana 2 procedurami oraz rozpoznaniem zasadniczym z listy dodatkowej  i dwoma roznymi rozpoznananiami
            // wspolistniejacymi z innej listy dodatkowej
            return recognision3procedures2;
        } else if(Condition.M.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision2procedures3 = size(recognitions) == 2 && size(procedures) == 3;
            boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit());
            return recognision2procedures3 && hospCond;
        } else if(Condition.N.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision2procedures1 = size(recognitions) == 2 && size(procedures) == 1;
            //?TODO co to znaczy rozpoznanie z listy dodatkowej?
            return recognision2procedures1;
        } else if(Condition.O.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision2procedures3 = size(recognitions) == 2 && size(procedures) == 3;
            boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit());
            return recognision2procedures3 && hospCond;
        } else if(Condition.P.equals(condition)) {
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognition1 = size(recognitions) == 1;
            boolean ageCond = checkAgeLimit(stay, AgeLimit.under18());
            return recognition1 && ageCond;
        } else if(Condition.Q.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision1procedures1 = size(recognitions) == 1 && size(procedures) == 1;
            boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit());
            return recognision1procedures1 && hospCond;
        } else if(Condition.R.equals(condition)) {
            //nie wiem :D
            return false;
        } else if(Condition.S.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision1procedures2 = size(recognitions) == 1 && size(procedures) == 2;
            return recognision1procedures2;
        } else if(Condition.T.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision1procedures3 = size(recognitions) == 1 && size(procedures) == 3;
            return recognision1procedures3;
        } else if(Condition.U.equals(condition)) {
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision2 = size(recognitions) == 2;
            boolean ageCond = checkAgeLimit(stay, parameter.getAgeLimit());
            return recognision2 && ageCond;
        } else if(Condition.V.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision1procedures1 = size(recognitions) == 1 && size(procedures) == 1;
            boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit());
            return recognision1procedures1 && hospCond;
        } else if(Condition.W.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognision1procedures1 = size(procedures) == 2 || (size(recognitions) == 1 && size(procedures) == 1);
            return recognision1procedures1;
        } else if(Condition.X.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognition2procedure1 = size(recognitions) == 2 && size(procedures) == 1;
            boolean ageCond = checkAgeLimit(stay, parameter.getAgeLimit());
            boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit());
            return recognition2procedure1 && ageCond && hospCond;
        } else if(Condition.Y.equals(condition)) {
            List<ICD9Wrapper> procedures = stay.getProcedures();
            List<ICD10Wrapper> recognitions = stay.getRecognitions();
            boolean recognitionOrProcedure = size(recognitions) == 1 || size(procedures) == 1;
            boolean ageCond = checkAgeLimit(stay, parameter.getAgeLimit());
            boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit());
            return recognitionOrProcedure && ageCond && hospCond;
        } else if(Condition.Z.equals(condition)) {
            //nie wiem!
            return false;
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
            int time = stay.getEpisode().hospitalTime(hospLimit.getTimeUnit());
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
    private List<ICD9Wrapper> procedures(List<Stay> stays) {
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

    private boolean hasRangeGreaterThen(List<ICD9Wrapper> procedures, RangeCondition rangeCondition) {
        for(ICD9Wrapper icd9Wrapper : procedures) {
            if(rangeCondition.greaterThen(icd9Wrapper.getIcd9().getRange())) {
                return true;
            }
        }
        return false;
    }

    private boolean hasRangeEqualsTo(List<ICD9Wrapper> procedures, RangeCondition rangeCondition) {
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
