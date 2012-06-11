package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

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

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 3);
        boolean proceduresSize = checkProceduresSize(procedures, 2);
        boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode());
        boolean coexist1Recognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode());
        boolean coexist2Recognition = checkExistRecognition(recognitions, parameter.getSecondICD10ListCode());
        boolean elseLists = false;
        if(recognitionsSize && proceduresSize && mainRecognition && coexist1Recognition && coexist2Recognition) {
            elseLists = checkSameLists(recognitions.get(0), recognitions.get(1), false)
                    && checkSameLists(recognitions.get(0), recognitions.get(2), false);
        }
        return recognitionsSize && proceduresSize && mainRecognition
                && coexist1Recognition && coexist2Recognition && elseLists;
    }
}