package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class HospitalLimit {
    private int id;
    private Integer under;
    private Integer over;
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

    public boolean test(int time) {
        if(under != null && over != null) {
            return time < under && time > over;
        }
        if(under != null && over == null) {
            return time < under;
        }
        if(under == null && over != null) {
            return time > over;
        }
        throw new IllegalStateException("under and over properties are both null!");
    }

    public static HospitalLimit under2Days() {
        HospitalLimit limit = new HospitalLimit();
        limit.setUnder(2);
        limit.setTimeUnit(TimeUnit.DAY);
        return limit;
    }
}
