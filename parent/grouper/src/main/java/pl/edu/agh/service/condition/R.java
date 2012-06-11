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
public class R extends AbstractChecker {
    public R() {
        super(Condition.R);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitions2Size = checkRecognitionsSize(recognitions, 2, reasons);
        boolean recognitions3Size = checkRecognitionsSize(recognitions, 3, reasons);
        boolean coexistRecognition = false;
        if(recognitions2Size) {
            coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
        }
        if (recognitions3Size) {
            coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons) &&
                                  checkExistRecognition(recognitions, parameter.getSecondICD10ListCode(), ICDCondition.SECOND_ICD10, reasons);
        }
        return coexistRecognition && (recognitions2Size || recognitions3Size);
    }
}