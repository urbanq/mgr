package pl.edu.agh.service;

import pl.edu.agh.domain.*;

import java.util.List;

/**
 * @author mateusz
 * @date 11.07.12
 */
public interface JGPService {

    public List<JGP> findJGP(final JGPFilter filter);

    public JGPGroupResult group(Episode episode);

    public JGPGroupResult doByProcedures(Episode episode);

    public JGPGroupResult doByRecognitions(Episode episode);

    public void resolveResultsByJGP(Stay stay, List<JGPParameter> parameters, JGPGroupResult jgpGroupResult);

    public void recountManDay(Episode episode, List<JGPResult> jgpResultList);
}
