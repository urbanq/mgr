package pl.edu.agh.service.condition;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import pl.edu.agh.domain.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class Z extends AbstractChecker {
    public Z() {
        super(Condition.Z);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        List<ICD9Wrapper> procedures = stay.getProcedures();
        List<ICD10Wrapper> recognitions = stay.getRecognitions();

        boolean proceduresSize = checkProceduresSize(procedures, 4);
        boolean recognitionsSize = checkRecognitionsSize(recognitions, 1);
        boolean mainRecognition  = checkExistRecognition(recognitions, parameter.getMainICD10ListCode());
        boolean additional4Procedures = checkSameDateProcedure(procedures, parameter.getFirstICD9ListCode(), parameter.getSecondICD9ListCode());
        return proceduresSize && recognitionsSize && mainRecognition && additional4Procedures;
    }

    /**
     * check if exist procedure with list code
     */
    private boolean checkSameDateProcedure(List<ICD9Wrapper> procedures, String firstList, String secondList) {
        if (StringUtils.isBlank(firstList) || StringUtils.isBlank(secondList)) {
            return false;
        }
        List<ICD9Wrapper> firstCode = new ArrayList<ICD9Wrapper>();
        List<ICD9Wrapper> secondCode = new ArrayList<ICD9Wrapper>();
        for (ICD9Wrapper icd9 : procedures) {
            if (checkExistProcedure(Arrays.asList(icd9), firstList)) {
                firstCode.add(icd9);
            }
            if (checkExistProcedure(Arrays.asList(icd9), secondList)) {
                secondCode.add(icd9);
            }
        }
        int occured = 0;
        Iterator<ICD9Wrapper> firstIter = firstCode.iterator();
        while (firstIter.hasNext()) {
            ICD9Wrapper firstICD9 = firstIter.next();
            Iterator<ICD9Wrapper> secondIter = secondCode.iterator();
            while (secondIter.hasNext()) {
                ICD9Wrapper secondICD9 = secondIter.next();
                if(DateUtils.isSameDay(firstICD9.getProcedureDate(), secondICD9.getProcedureDate())) {
                    occured++;
                    firstIter.remove();
                    secondIter.remove();
                }
            }
        }
        return occured > 1;
    }
}