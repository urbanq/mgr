package pl.edu.agh.domain.reason;

import pl.edu.agh.domain.Condition;
import pl.edu.agh.domain.ListType;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class ICDSizeReason extends DirectionalReason<Integer> {
    private final ListType listType;

    public ICDSizeReason(Integer required, ListType listType, Condition condition) {
        super(required, condition);
        this.listType = listType;
    }

    public ListType listType() {
        return listType;
    }
}
