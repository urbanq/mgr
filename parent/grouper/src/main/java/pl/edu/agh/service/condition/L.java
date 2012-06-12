package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;
import pl.edu.agh.domain.condition.ICDCondition;
import pl.edu.agh.domain.reason.Reason;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class L extends AbstractChecker {
    public L() {
        super(Condition.L);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 3, reasons);
        boolean proceduresSize = checkProceduresSize(procedures, 2, reasons);
        boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
        boolean coexist1Recognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
        boolean coexist2Recognition = checkExistRecognition(recognitions, parameter.getSecondICD10ListCode(), ICDCondition.SECOND_ICD10, reasons);
        boolean additionalProcedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);
        boolean elseLists = false;
        if(recognitionsSize && proceduresSize && mainRecognition && coexist1Recognition && coexist2Recognition) {
            elseLists = checkSameLists(recognitions.get(0), recognitions.get(1), false, reasons)
                    && checkSameLists(recognitions.get(0), recognitions.get(2), false, reasons);
        }
        boolean hospCond = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
        boolean ageCond = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
        return recognitionsSize && proceduresSize && mainRecognition &&
                coexist1Recognition && coexist2Recognition && additionalProcedure &&
                elseLists && hospCond && ageCond;
    }
}