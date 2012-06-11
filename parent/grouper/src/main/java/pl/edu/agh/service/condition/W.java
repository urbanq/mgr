package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class W extends AbstractChecker {
    public W() {
        super(Condition.W);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean procedures1Size = checkProceduresSize(procedures, 1);
        boolean recognitions1Size = checkRecognitionsSize(recognitions, 1);
        boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode());

        boolean procedures2Size = checkProceduresSize(procedures, 2);
        boolean additionalProcedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode());

        return (procedures1Size && recognitions1Size && mainRecognition) ||
                (procedures2Size && additionalProcedure);
    }
}