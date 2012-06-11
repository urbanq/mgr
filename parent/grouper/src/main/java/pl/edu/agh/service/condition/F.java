package pl.edu.agh.service.condition;

import pl.edu.agh.domain.Condition;
import pl.edu.agh.domain.ICD9Wrapper;
import pl.edu.agh.domain.JGPParameter;
import pl.edu.agh.domain.Stay;
import pl.edu.agh.domain.condition.ICDCondition;
import pl.edu.agh.domain.reason.Reason;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class F extends AbstractChecker {
    public F() {
        super(Condition.F);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();

        boolean proceduresSize = checkProceduresSize(stay.getProcedures(), 2);
        boolean additionalProcedure = checkExistProcedure(stay.getProcedures(), parameter.getFirstICD9ListCode(), ICDCondition.FIRST_ICD9, reasons);
        boolean elseLists = false;
        if(proceduresSize && additionalProcedure) {
            elseLists = checkSameLists(procedures.get(0), procedures.get(1), false, reasons);
        }
        return proceduresSize && additionalProcedure && elseLists;
    }
}