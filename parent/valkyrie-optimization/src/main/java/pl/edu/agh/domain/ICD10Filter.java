package pl.edu.agh.domain;

import pl.edu.agh.dao.GenericFilter;

public class ICD10Filter implements GenericFilter<ICD10> {
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
}
