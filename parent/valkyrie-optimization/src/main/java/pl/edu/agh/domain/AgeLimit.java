package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class AgeLimit {
    private int id;
    private int under;
    private int over;
    private TimeUnit timeUnit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUnder() {
        return under;
    }

    public void setUnder(int under) {
        this.under = under;
    }

    public int getOver() {
        return over;
    }

    public void setOver(int over) {
        this.over = over;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }
}
