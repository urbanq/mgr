package pl.edu.agh.domain.reason;

import pl.edu.agh.domain.AgeLimit;
import pl.edu.agh.domain.Condition;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class AgeReason extends DirectionalReason<AgeLimit> {
    public AgeReason(AgeLimit required, Condition condition) {
        super(required, condition);
    }
}
