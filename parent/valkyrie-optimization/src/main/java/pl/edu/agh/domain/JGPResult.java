package pl.edu.agh.domain;

import java.util.List;

/**
 * User: mateusz
 * Date: 21.05.12
 */
public class JGPResult {
    private List<JGPParameter> parameters;
    private Double value;
    private Stay stay;

    public List<JGPParameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<JGPParameter> parameters) {
        this.parameters = parameters;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Stay getStay() {
        return stay;
    }

    public void setStay(Stay stay) {
        this.stay = stay;
    }
}
