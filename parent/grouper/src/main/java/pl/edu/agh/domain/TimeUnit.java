package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public enum TimeUnit {
    DAY('D', 1)
    ,
    WEEK('T', 7)
    ,
    YEAR('R', 365)
    ;
    private char id;
    private int days;

    private TimeUnit(char id, int days) {
        this.id = id;
        this.days = days;
    }

    public int getDays() {
        return days;
    }

    public static final TimeUnit valueOf(char id) {
        for(TimeUnit timeUnit : values()) {
            if(timeUnit.id == id) {
                return timeUnit;
            }
        }
        throw new IllegalArgumentException("id = " + id);
    }
}
