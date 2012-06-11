package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class X extends AbstractChecker {
    public X() {
        super(Condition.X);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean procedures1Size = checkProceduresSize(procedures, 1);
        boolean recognitionsSize = checkRecognitionsSize(recognitions, 2);
        boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode());
        boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode());
        boolean additionalProcedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode());
        boolean ageLimit = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
        boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
        return procedures1Size && recognitionsSize && mainRecognition &&
                coexistRecognition && additionalProcedure &&
                ageLimit && hospLimit;
    }
}