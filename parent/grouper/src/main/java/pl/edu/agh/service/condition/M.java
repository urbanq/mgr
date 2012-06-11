package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class M extends AbstractChecker {
    public M() {
        super(Condition.M);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 2);
        boolean proceduresSize = checkProceduresSize(procedures, 3);
        boolean additional1Procedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode());
        boolean additional2Procedure = checkExistProcedure(procedures, parameter.getSecondICD9ListCode());
        boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode());
        boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode());
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
    }
}