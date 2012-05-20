package pl.edu.agh.domain;

/**
 * Rozpoznanie ICD10
 */
public class ICD10 implements Comparable<ICD10> {
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

    @Override
    public int compareTo(ICD10 o) {
        return name.compareTo(o.name);
    }
}
