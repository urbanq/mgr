package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class AgeLimit {
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

    public void setUnder(Integer under) {
        this.under = under;
    }

    public int getOver() {
        return over;
    }

    public void setOver(Integer over) {
        this.over = over;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public boolean test(int age) {
        if(under != null && over != null) {
            return age < under && age > over;
        }
        if(under != null && over == null) {
            return age < under;
        }
        if(under == null && over != null) {
            return age > over;
        }
        throw new IllegalStateException("under and over properties are both null!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgeLimit ageLimit = (AgeLimit) o;

        if (id != ageLimit.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
