package pl.edu.agh.service.condition;

import pl.edu.agh.domain.ListType;

/**
 * @author mateusz
 * @date 11.06.12
 */
public enum ICDCondition {
    MAIN_ICD10(ListType.ICD10),
    FIRST_ICD10(ListType.ICD10),
    SECOND_ICD10(ListType.ICD10),
    FIRST_ICD9(ListType.ICD9),
    SECOND_ICD9(ListType.ICD9),
    NEGATIVE_ICD9(ListType.ICD9),
    NEGATIVE_ICD10(ListType.ICD10),
    SAME_ICD9(ListType.ICD9),
    SAME_ICD10(ListType.ICD10),
    ELSE_ICD9(ListType.ICD9),
    ELSE_ICD10(ListType.ICD10);

    private final ListType listType;

    private ICDCondition(ListType listType) {
        this.listType = listType;
    }

    public ListType listType() {
        return listType;
    }
}
