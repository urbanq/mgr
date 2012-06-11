package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;
import pl.edu.agh.domain.condition.ICDCondition;
import pl.edu.agh.domain.reason.Reason;

import java.util.Arrays;
import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class D extends AbstractChecker {
    public D() {
        super(Condition.D);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 1, reasons);
        boolean proceduresSize = checkProceduresSize(procedures, 2, reasons);
        boolean sameLists = false;
        if(recognitionsSize && proceduresSize) {
            sameLists = checkExistProcedure(Arrays.asList(procedures.get(0)), parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons) &&
                        checkExistProcedure(Arrays.asList(procedures.get(1)), parameter.getSecondICD9ListCode(), ICDCondition.SECOND_ICD9, reasons);
            if (sameLists) {
                sameLists = checkSameLists(procedures.get(0), procedures.get(1), true, reasons);
            }
        }
        boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
        return recognitionsSize && proceduresSize && sameLists && hospLimit;
    }
}
