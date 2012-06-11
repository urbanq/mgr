package pl.edu.agh.domain.reason;

import pl.edu.agh.domain.Condition;

/**
 * @author mateusz
 * @date 11.06.12
 */
public abstract class DirectionalReason<T> extends Reason<T> {
    private final Condition condition;

    public DirectionalReason(T required, Condition condition) {
        super(required);
        this.condition = condition;
    }

    public Condition condition() {
        return condition;
    }
}
