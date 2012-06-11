package pl.edu.agh.service.reason;

import pl.edu.agh.domain.ListType;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class NegativeICDReason extends Reason<String> {
    private final ListType listType;

    public NegativeICDReason(String listCode, ListType listType) {
        super(listCode);
        this.listType = listType;
    }

    public ListType listType() {
        return listType;
    }
}
