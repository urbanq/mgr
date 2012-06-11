package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class P extends AbstractChecker {
    public P() {
        super(Condition.P);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
        boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode());
        boolean ageLimit = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
        return recognitionsSize && mainRecognition && ageLimit;
    }
}