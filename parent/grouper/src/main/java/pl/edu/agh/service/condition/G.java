package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class G extends AbstractChecker {
    public G() {
        super(Condition.G);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
        boolean proceduresSize = checkProceduresSize(procedures, 2);
        boolean additionalProcedure = checkExistProcedure(procedures, parameter.getFirstICD9ListCode());
        boolean mainRecognition = checkExistRecognition(recognitions, parameter.getMainICD10ListCode());
        boolean ageLimit = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
        boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
        return recognitionsSize && proceduresSize && additionalProcedure && mainRecognition && ageLimit && hospLimit;
    }
}