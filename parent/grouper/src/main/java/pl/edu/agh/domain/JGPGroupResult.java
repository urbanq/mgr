package pl.edu.agh.domain;

import org.valkyriercp.command.ActionCommandExecutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author mateusz
 * @date 07.06.12
 */
public class JGPGroupResult implements ActionCommandExecutor {
    private List<JGPResult> accepted = new ArrayList<JGPResult>();
    private List<JGPResult> notAccepted = new ArrayList<JGPResult>();

    // generate to visualize for user:
    private JGPResult best;
    //not accepted
    private List<JGPResult> cheap = new ArrayList<JGPResult>();
    private List<JGPResult> expensive = new ArrayList<JGPResult>();

    public List<JGPResult> accepted() {
        return accepted;
    }

    public List<JGPResult> notAccepted() {
        return notAccepted;
    }

    /**
     * sort result, count max
     */
    @Override
    public void execute() {
        if (!accepted.isEmpty()) {
            best = JGPResult.max(accepted);
        }
        if (best != null) {
            best = JGPResult.max(accepted);
            for (JGPResult result : notAccepted) {
                if (best.compareTo(result) < 0) {
                    cheap.add(result);
                }
                if (best.compareTo(result) > 0) {
                    expensive.add(result);
                }
            }
        }
    }

    public List<JGPResult> getAccepted() {
        return accepted;
    }

    public void setAccepted(List<JGPResult> accepted) {
        this.accepted = accepted;
    }

    public List<JGPResult> getCheap() {
        return cheap;
    }

    public void setCheap(List<JGPResult> cheap) {
        this.cheap = cheap;
    }

    public List<JGPResult> getExpensive() {
        return expensive;
    }

    public void setExpensive(List<JGPResult> expensive) {
        this.expensive = expensive;
    }

    public List<JGPResult> getBest() {
        return Collections.singletonList(best);
    }

    public void setBest(List<JGPResult> best) {
        this.best = best.get(0);
    }
}
