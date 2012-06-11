package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;
import pl.edu.agh.domain.condition.ICDCondition;
import pl.edu.agh.domain.reason.Reason;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class K extends AbstractChecker {
    public K() {
        super(Condition.K);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 2, reasons);
        boolean proceduresSize = checkProceduresSize(procedures, 1, reasons);
        boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode(), ICDCondition.MAIN_ICD10, reasons);
        boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode(), ICDCondition.FIRST_ICD10, reasons);
        boolean elseLists = false;
        if(recognitionsSize && proceduresSize && mainRecognition && coexistRecognition) {
            elseLists = checkSameLists(recognitions.get(0), recognitions.get(1), false, reasons);
        }
        boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
        return recognitionsSize && proceduresSize && mainRecognition && coexistRecognition && elseLists && hospLimit;
    }
}