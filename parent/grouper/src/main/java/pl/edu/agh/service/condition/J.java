package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class J extends AbstractChecker {
    public J() {
        super(Condition.J);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
        boolean proceduresSize = checkProceduresSize(procedures, 3);
        boolean mainRecognition = checkExistRecognition(recognitions, parameter.getMainICD10ListCode());
        boolean additional1Procedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode());
        boolean additional2Procedure = checkExistProcedure(procedures, parameter.getSecondICD9ListCode());
        boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
        return recognitionsSize && proceduresSize && mainRecognition
                && additional1Procedure && additional2Procedure && hospLimit;
    }
}