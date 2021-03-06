package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;
import pl.edu.agh.domain.condition.ICDCondition;
import pl.edu.agh.domain.reason.Reason;

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

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 2, reasons);
        boolean proceduresSize   = checkProceduresSize(procedures, 2, reasons);
        boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
        boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
        boolean additionalProcedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);
        return recognitionsSize && proceduresSize && mainRecognition && coexistRecognition && additionalProcedure;
    }
}
