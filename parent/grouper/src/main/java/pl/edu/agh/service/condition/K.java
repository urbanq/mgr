package pl.edu.agh.service.condition;

import pl.edu.agh.domain.*;

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

        boolean recognitionsSize = checkRecognitionsSize(recognitions, 2);
        boolean proceduresSize = checkProceduresSize(procedures, 1);
        boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode());
        boolean coexistRecognition = checkExistRecognition(recognitions, parameter.getFirstICD10ListCode());
        boolean elseLists = false;
        if(recognitionsSize && proceduresSize && mainRecognition && coexistRecognition) {
            elseLists = checkSameLists(recognitions.get(0), recognitions.get(1), false);
        }
        boolean hospLimit = checkHospitalLimit(stay, parameter.getHospitalLimit(), reasons);
        return recognitionsSize && proceduresSize && mainRecognition && coexistRecognition && elseLists && hospLimit;
    }
}