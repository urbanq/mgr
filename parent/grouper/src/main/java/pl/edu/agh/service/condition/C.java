package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class C extends AbstractChecker {
    public C() {
        super(Condition.C);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 2);
        boolean proceduresSize   = checkProceduresSize(procedures, 2);
        boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode());
        if (!mainRecognition) {
            reasons.add(new Reason(condition(), JGPParameter.ICDCondition.MAIN_ICD10, parameter.getMainICD10ListCode()));
        }
        boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode());
        if (!coexistRecognition) {
            reasons.add(new Reason(condition(), JGPParameter.ICDCondition.FIRST_ICD10, parameter.getFirstICD10ListCode()));
        }
        return recognitionsSize && proceduresSize && mainRecognition && coexistRecognition;
    }
}
