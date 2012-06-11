package pl.edu.agh.service.condition;

import pl.edu.agh.domain.Condition;
import pl.edu.agh.domain.JGPParameter;
import pl.edu.agh.domain.Reason;
import pl.edu.agh.domain.Stay;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class D extends AbstractChecker {
    public D() {
        super(Condition.D);
    }

    @Override
    public boolean checkCondition(Stay stay, JGPParameter parameter, List<Reason> reasons) {
        return false;
    }
}
