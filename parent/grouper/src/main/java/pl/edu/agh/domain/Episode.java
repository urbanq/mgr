package pl.edu.agh.domain;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Weeks;
import org.joda.time.Years;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * hospitalization, episode
 * @author mateusz
 */
public class Episode {
    private Date dateOfBirth;
    private Sex sex = Sex.NO_DATA;

    private Date incomeDate;
    private IncomeMode incomeMode;

    private Date outcomeDate;
    private OutcomeMode outcomeMode;

    private HospitalType hospitalType = HospitalType.NORMAL;

    private List<Stay> stays;

    public Episode() {
        stays = new ArrayList<Stay>();
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    public IncomeMode getIncomeMode() {
        return incomeMode;
    }

    public void setIncomeMode(IncomeMode incomeMode) {
        this.incomeMode = incomeMode;
    }

    public Date getOutcomeDate() {
        return outcomeDate;
    }

    public void setOutcomeDate(Date outcomeDate) {
        this.outcomeDate = outcomeDate;
    }

    public OutcomeMode getOutcomeMode() {
        return outcomeMode;
    }

    public void setOutcomeMode(OutcomeMode outcomeMode) {
        this.outcomeMode = outcomeMode;
    }

    public HospitalType getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(HospitalType hospitalType) {
        this.hospitalType = hospitalType;
    }

    public List<Stay> getStays() {
        return stays;
    }

    public void setStays(List<Stay> stays) {
        this.stays = stays;
    }

    public int age(TimeUnit timeUnit) {
        DateTime birth = new DateTime(dateOfBirth);
        DateTime now   = DateTime.now();
        switch (timeUnit){
            case DAY:
                Days days = Days.daysBetween(birth, now);
                return days.getDays();
            case WEEK:
                Weeks weeks = Weeks.weeksBetween(birth, now);
                return weeks.getWeeks();
            case YEAR:
                Years years = Years.yearsBetween(birth, now);
                return years.getYears();
            default:
                throw new IllegalArgumentException("age not implemented for time unit: " + timeUnit);
        }
    }

    /**
     * @return hospitalization time
     */
    public int hospitalTime(TimeUnit timeUnit) {
        DateTime income = new DateTime(incomeDate);
        DateTime outcome   = new DateTime(outcomeDate);
        switch (timeUnit){
            case DAY:
                Days days = Days.daysBetween(income, outcome);
                return days.getDays();
            case WEEK:
                Weeks weeks = Weeks.weeksBetween(income, outcome);
                return weeks.getWeeks();
            case YEAR:
                Years years = Years.yearsBetween(income, outcome);
                return years.getYears();
            default:
                throw new IllegalArgumentException("time not implemented for time unit: " + timeUnit);
        }
    }
}
