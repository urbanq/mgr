package pl.edu.agh.domain;


public class ICD9 implements Comparable<ICD9> {
    private String code;
    private String name;
    private int range;

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

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    @Override
    public int compareTo(ICD9 o) {
        return name.compareTo(o.name);
    }
}
