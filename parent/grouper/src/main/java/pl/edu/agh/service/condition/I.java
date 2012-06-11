package pl.edu.agh.service.condition;

import pl.edu.agh.domain.Condition;
import pl.edu.agh.domain.ICD9Wrapper;
import pl.edu.agh.domain.JGPParameter;
import pl.edu.agh.domain.Stay;
import pl.edu.agh.service.reason.Reason;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class I extends AbstractChecker {
    public I() {
        super(Condition.I);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();

        boolean proceduresSize = checkProceduresSize(procedures, 3);
        boolean additional1Procedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);
        boolean additional2Procedure = checkExistProcedure(procedures, parameter.getSecondICD9ListCode(), ICDCondition.SECOND_ICD9, reasons);
        boolean elseLists = false;
        if(proceduresSize && additional1Procedure && additional2Procedure) {
            elseLists = checkSameLists(procedures.get(0), procedures.get(1), false, reasons)
                    && checkSameLists(procedures.get(0), procedures.get(2), false, reasons);
        }
        return proceduresSize && additional1Procedure && additional2Procedure && elseLists;
    }
}