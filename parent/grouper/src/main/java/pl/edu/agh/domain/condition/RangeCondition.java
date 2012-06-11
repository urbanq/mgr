package pl.edu.agh.domain.condition;

/**
 * @author mateusz
 * @date 11.06.12
 */
public enum RangeCondition {
    RANGE_0(0),
    RANGE_2(2);

    private final Integer range;

    private RangeCondition(Integer range) {
        this.range = range;
    }

    public boolean greaterThen(Integer rangeToCheck) {
        return rangeToCheck > range;
    }

    public boolean equalsTo(Integer rangeToCheck) {
        return rangeToCheck == range;
    }
}
