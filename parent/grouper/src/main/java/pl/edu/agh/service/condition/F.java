package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

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
        boolean additionalProcedure = checkExistProcedure(stay.getProcedures(), parameter.getFirstICD9ListCode());
        boolean elseLists = false;
        if(proceduresSize && additionalProcedure) {
            elseLists = checkSameLists(procedures.get(0), procedures.get(1), false);
        }
        return proceduresSize && additionalProcedure && elseLists;
    }
}