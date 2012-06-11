package pl.edu.agh.domain;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * User: mateusz
 * Date: 21.05.12
 */
public class JGPResult implements Comparable<JGPResult> {
    private JGP jgp;
    private Double value;
    private Stay stay;
    private List<Reason> reasons = new ArrayList<Reason>();

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

    public JGP getJgp() {
        return jgp;
    }

    public void setJgp(JGP jgp) {
        this.jgp = jgp;
    }

    public List<Reason> reasons() {
        return reasons;
    }

    public static JGPResult max(List<JGPResult> jgpResultList) {
        if(CollectionUtils.isEmpty(jgpResultList)) {
            return null;
        } else {
            JGPResult maxValue = Collections.max(jgpResultList, new Comparator<JGPResult>() {
                @Override
                public int compare(JGPResult o1, JGPResult o2) {
                    return o1.getValue().compareTo(o2.getValue());
                }
            });
            return maxValue;
        }
    }

    @Override
    public int compareTo(JGPResult o) {
        return value.compareTo(o.value);
    }
}
