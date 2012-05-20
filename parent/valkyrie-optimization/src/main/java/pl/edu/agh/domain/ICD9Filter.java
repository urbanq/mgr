package pl.edu.agh.domain;

import pl.edu.agh.dao.GenericFilter;

/**
 * User: mateusz
 * Date: 12.05.12
 */
public class ICD9Filter implements GenericFilter<ICD9> {
    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ICD9Filter fromICD9(ICD9 icd9) {
        ICD9Filter filter = new ICD9Filter();
        filter.setName(icd9.getName());
        filter.setCode(icd9.getCode());
        return filter;
    }
}
