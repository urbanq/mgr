package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

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

        boolean recognitions2Size = checkRecognitionsSize(recognitions, 2);
        boolean recognitions3Size = checkRecognitionsSize(recognitions, 3);
        boolean coexistRecognition = false;
        if(recognitions2Size) {
            coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode());
        }
        if (recognitions3Size) {
            coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode())
                    && checkExistRecognition(recognitions, parameter.getSecondICD10ListCode());
        }
        return coexistRecognition && (recognitions2Size || recognitions3Size);
    }
}