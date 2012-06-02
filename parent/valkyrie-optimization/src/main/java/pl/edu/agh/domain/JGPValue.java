package pl.edu.agh.domain;

import java.util.EnumMap;
import java.util.Map;

/**
 * User: mateusz
 * Date: 02.06.12
 */
public class JGPValue {
    private JGP jgp;
    private Map<HospitalType, Integer> values = new EnumMap<HospitalType, Integer>(HospitalType.class);

    public JGP getJgp() {
        return jgp;
    }

    public void setJgp(JGP jgp) {
        this.jgp = jgp;
    }

    public Integer getValue(HospitalType hospitalType) {
        return values.get(hospitalType);
    }

    public void setValue(HospitalType hospitalType, Integer value) {
        values.put(hospitalType, value);
    }
}
