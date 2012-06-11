package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class T extends AbstractChecker {
    public T() {
        super(Condition.T);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
        boolean proceduresSize = checkProceduresSize(procedures, 3);
        boolean coexistRecognition  = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode());
        return recognitionsSize && proceduresSize && coexistRecognition;
    }
}