package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

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
        boolean additional1Procedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode());
        boolean additional2Procedure = checkExistProcedure(procedures, parameter.getSecondICD9ListCode());
        boolean elseLists = false;
        if(proceduresSize && additional1Procedure && additional2Procedure) {
            elseLists = checkSameLists(procedures.get(0), procedures.get(1), false)
                    && checkSameLists(procedures.get(0), procedures.get(2), false);
        }
        return proceduresSize && additional1Procedure && additional2Procedure && elseLists;
    }
}