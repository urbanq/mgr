package pl.edu.agh.domain;

import java.util.Date;

/**
 * User: mateusz
 * Date: 16.05.12
 */
public class GrouperFormObject {
    private Date dateOfBirth;
    private Sex sex = Sex.NO_DATA;

    private Date incomeDate;
    private IncomeModeLimit incomeModeLimit;

    private Date outcomeDate;
    private OutcomeModeLimit outcomeModeLimit;

    private HospitalType hospitalType;

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

    public IncomeModeLimit getIncomeModeLimit() {
        return incomeModeLimit;
    }

    public void setIncomeModeLimit(IncomeModeLimit incomeModeLimit) {
        this.incomeModeLimit = incomeModeLimit;
    }

    public Date getOutcomeDate() {
        return outcomeDate;
    }

    public void setOutcomeDate(Date outcomeDate) {
        this.outcomeDate = outcomeDate;
    }

    public OutcomeModeLimit getOutcomeModeLimit() {
        return outcomeModeLimit;
    }

    public void setOutcomeModeLimit(OutcomeModeLimit outcomeModeLimit) {
        this.outcomeModeLimit = outcomeModeLimit;
    }

    public HospitalType getHospitalType() {
        return hospitalType;
    }

    public void setHospitalType(HospitalType hospitalType) {
        this.hospitalType = hospitalType;
    }
}
