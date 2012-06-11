package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;
import pl.edu.agh.domain.reason.Reason;
import pl.edu.agh.domain.condition.RangeCondition;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class B extends AbstractChecker {
    public B() {
        super(Condition.B);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();

        boolean range2Equal = checkRangeEqualsTo(procedures, RangeCondition.RANGE_2, reasons);
        boolean hospLimit   = checkHospitalLimit(stay, HospitalLimit.under2Days(), reasons);
        boolean ageLimit    = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
        return range2Equal && hospLimit && ageLimit;
    }
}
