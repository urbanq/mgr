package pl.edu.agh.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mateusz
 * @date 07.06.12
 */
public class JGPGroupResult {
    private List<JGPResult> accepted = new ArrayList<JGPResult>();
    private List<JGPResult> notAccepted = new ArrayList<JGPResult>();

    public List<JGPResult> accepted() {
        return accepted;
    }

    public List<JGPResult> notAccepted() {
        return notAccepted;
    }
}
