package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;
import pl.edu.agh.domain.reason.*;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class Y extends AbstractChecker {
    public Y() {
        super(Condition.Y);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 1, reasons);
        boolean proceduresSize = checkProceduresSize(procedures, 1, reasons);
        boolean ageLimit = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
        boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
        return (recognitionsSize || proceduresSize) && ageLimit && hospLimit;
    }
}