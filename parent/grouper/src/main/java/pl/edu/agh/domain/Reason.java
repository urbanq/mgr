package pl.edu.agh.domain;

import java.util.ArrayList;
import java.util.List;

import static pl.edu.agh.domain.JGPParameter.ICDCondition;

/**
 * @author mateusz
 * @date 10.06.12
 */
public class Reason {
    /**
     * required age limit
     */
    private AgeLimit ageLimit;
    /**
     * required hospital time limit
     */
    private HospitalLimit hospitalLimit;
    /**
     * required sex
     */
    private Sex sexLimit;
    /**
     * required income mode
     */
    private IncomeMode incomeModeLimit;
    /**
     * required outcome mode
     */
    private OutcomeMode outcomeModeLimit;
    /**
     * required departments
     */
    private List<Department> departments;

    /**
     * required recognition or procedure (directional condition checkers)
     */
    private Condition condition;
    private ICDCondition icdCondition;
    private String listCode;

    /*
     * constructors
     */

    public Reason(AgeLimit ageLimit) {
        this.ageLimit = ageLimit;
    }

    public Reason(HospitalLimit hospitalLimit) {
        this.hospitalLimit = hospitalLimit;
    }

    public Reason(Sex sexLimit) {
        this.sexLimit = sexLimit;
    }

    public Reason(IncomeMode incomeModeLimit) {
        this.incomeModeLimit = incomeModeLimit;
    }

    public Reason(OutcomeMode outcomeModeLimit) {
        this.outcomeModeLimit = outcomeModeLimit;
    }

    public Reason(List<Department> departments) {
        this.departments = new ArrayList<Department>(departments);
    }

    public Reason(ICDCondition icdCondition, String listCode) {
        this.condition = condition;
        this.icdCondition = icdCondition;
        this.listCode = listCode;
    }
    public Reason(Condition condition) {
        this.condition = condition;
    }

    public Reason(Condition condition, ICDCondition icdCondition, String listCode) {
        this.condition = condition;
        this.icdCondition = icdCondition;
        this.listCode = listCode;
    }
}
