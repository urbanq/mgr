package pl.edu.agh.domain.reason;

import pl.edu.agh.domain.Condition;
import pl.edu.agh.domain.condition.RangeCondition;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class RangeReason extends DirectionalReason<RangeCondition> {
    public RangeReason(RangeCondition required, Condition condition) {
        super(required, condition);
    }
}
