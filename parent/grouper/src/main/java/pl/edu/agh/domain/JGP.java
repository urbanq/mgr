package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public class JGP  implements Comparable<JGP> {
    private String code;
    private String productCode;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(JGP o) {
        return code.compareToIgnoreCase(o.code);
    }
}
