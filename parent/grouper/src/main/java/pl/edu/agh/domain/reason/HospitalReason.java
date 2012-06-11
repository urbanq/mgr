package pl.edu.agh.domain.reason;

import pl.edu.agh.domain.Condition;
import pl.edu.agh.domain.HospitalLimit;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class HospitalReason extends DirectionalReason<HospitalLimit> {
    public HospitalReason(HospitalLimit required, Condition condition) {
        super(required, condition);
    }
}
