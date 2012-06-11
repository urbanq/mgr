package pl.edu.agh.service.condition;


import pl.edu.agh.domain.*;
import pl.edu.agh.domain.reason.Reason;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class A extends AbstractChecker {
    public A() {
        super(Condition.A);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 1, reasons);
        boolean proceduresSize = checkProceduresSize(procedures, 1, reasons);
        return recognitionsSize || proceduresSize;
    }
}
