package pl.edu.agh.domain;

import java.util.Date;

/**
 * User: mateusz
 * Date: 16.05.12
 */
public class GrouperFormObject {
    private Date dateOfBirth;
    private Sex sex = Sex.NO_DATA;

    private Date income;

    private Date outcome;

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

    public Date getIncome() {
        return income;
    }

    public void setIncome(Date income) {
        this.income = income;
    }

    public Date getOutcome() {
        return outcome;
    }

    public void setOutcome(Date outcome) {
        this.outcome = outcome;
    }

    //    private Department department;

//    public Department getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(Department department) {
//        this.department = department;
//    }
}
