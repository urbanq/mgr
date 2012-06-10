package pl.edu.agh.domain;

import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * User: mateusz
 * Date: 21.05.12
 */
public class JGPResult {
    private JGP jgp;
    private Double value;
    private Stay stay;
//    private String listCode; / warunek glowny
//    List<ExclusionReason> exclusionReasons

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
}
