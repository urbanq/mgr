package pl.edu.agh.service.reason;

import pl.edu.agh.domain.Condition;
import pl.edu.agh.service.condition.ICDCondition;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class ICDReason extends DirectionalReason<String> {
    private final ICDCondition icdCondition;

    public ICDReason(String required, ICDCondition icdCondition, Condition condition) {
        super(required, condition);
        this.icdCondition = icdCondition;
    }

    public ICDCondition icdCondition() {
        return icdCondition;
    }
}
