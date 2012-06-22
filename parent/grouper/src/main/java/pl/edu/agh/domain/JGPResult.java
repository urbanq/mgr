package pl.edu.agh.domain;

import org.apache.commons.collections.CollectionUtils;
import pl.edu.agh.domain.reason.Reason;

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

    public List<Reason> getReasons() {
        return reasons;
    }

    public void setReasons(List<Reason> reasons) {
        this.reasons = reasons;
    }

    public List<Reason> reasons() {
        return reasons;
    }

    public <T extends Reason> List<T> reasons(Class<T> reasonClass) {
        List<T> result = new ArrayList<T>();
        for(Reason reason : reasons) {
            if(reasonClass.equals(reason.getClass())) {
                result.add((T) reason);
            }
        }
        return result;
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
        return o.value.compareTo(value);
    }
}
