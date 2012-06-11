package pl.edu.agh.service.reason;

import pl.edu.agh.domain.Condition;
import pl.edu.agh.service.condition.RangeCondition;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class RangeReason extends DirectionalReason<RangeCondition> {
    public RangeReason(RangeCondition required, Condition condition) {
        super(required, condition);
    }
}
