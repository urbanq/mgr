package pl.edu.agh.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public class Stay implements Comparable<Stay> {
    private Department department;
    private Service service;

    private Date incomeDate;
    private Date outcomeDate;

    private List<ICD10Wrapper> recognitions = new ArrayList<ICD10Wrapper>();
    private List<ICD9Wrapper> procedures = new ArrayList<ICD9Wrapper>();

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

    public List<ICD10Wrapper> getRecognitions() {
        return recognitions;
    }

    public void setRecognitions(List<ICD10Wrapper> recognitions) {
        this.recognitions = recognitions;
    }

    public List<ICD9Wrapper> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<ICD9Wrapper> procedures) {
        this.procedures = procedures;
    }

    @Override
    public int compareTo(Stay o) {
        return department.getName().compareToIgnoreCase(o.department.getName());
    }
}
