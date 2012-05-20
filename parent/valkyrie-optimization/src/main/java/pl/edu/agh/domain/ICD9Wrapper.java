package pl.edu.agh.domain;

import java.util.Date;

/**
 * User: mateusz
 * Date: 20.05.12
 */
public class ICD9Wrapper implements Comparable<ICD9Wrapper> {
    private ICD9 icd9;
    private Date procedureDate;

    public ICD9 getIcd9() {
        return icd9;
    }

    public void setIcd9(ICD9 icd9) {
        this.icd9 = icd9;
    }

    public Date getProcedureDate() {
        return procedureDate;
    }

    public void setProcedureDate(Date procedureDate) {
        this.procedureDate = procedureDate;
    }

    @Override
    public int compareTo(ICD9Wrapper o) {
        return procedureDate.compareTo(o.procedureDate);
    }
}
