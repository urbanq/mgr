package pl.edu.agh.service.condition;

import pl.edu.agh.domain.Condition;
import pl.edu.agh.domain.ICD10Wrapper;
import pl.edu.agh.domain.JGPParameter;
import pl.edu.agh.domain.Stay;
import pl.edu.agh.domain.condition.ICDCondition;
import pl.edu.agh.domain.reason.Reason;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class U extends AbstractChecker {
    public U() {
        super(Condition.U);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 2, reasons);
        boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
        boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
        boolean ageLimit = checkAgeLimit(stay, parameter.getAgeLimit(), reasons);
        return recognitionsSize && mainRecognition && coexistRecognition && ageLimit;
    }
}