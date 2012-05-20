package pl.edu.agh.domain;

import java.util.Date;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public class Visit implements Comparable<Visit> {
    private Department department;
    private Service service;

    private Date incomeDate;
    private Date outcomeDate;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Date getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(Date incomeDate) {
        this.incomeDate = incomeDate;
    }

    public Date getOutcomeDate() {
        return outcomeDate;
    }

    public void setOutcomeDate(Date outcomeDate) {
        this.outcomeDate = outcomeDate;
    }

    @Override
    public int compareTo(Visit o) {
        return department.getName().compareToIgnoreCase(o.department.getName());
    }
}
