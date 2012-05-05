package pl.edu.agh.test.simulated.annealing;

import junit.framework.TestCase;
import pl.edu.agh.simulated.annealing.MockComplexSimulatedAnnealingProblem;
import pl.edu.agh.simulated.annealing.MockSimpleSimulatedAnnealingProblem;
import pl.edu.agh.simulated.annealing.SimulatedAnnealing;


public class SimulatedAnnealingTest extends TestCase {
    private static final double SOLUTION_DELTA = 0.0001;

    private MockSimpleSimulatedAnnealingProblem simpleProblem;
    private MockComplexSimulatedAnnealingProblem complexProblem;
    private SimulatedAnnealing simulatedAnnealing;

    @Override
    protected void setUp() throws Exception {
        simpleProblem = new MockSimpleSimulatedAnnealingProblem();
        complexProblem = new MockComplexSimulatedAnnealingProblem();

    }

    public void testSimpleSimulatedAnnealing() {
        simulatedAnnealing = new SimulatedAnnealing(simpleProblem);
        simpleProblem.setTargetNumber(3);
        simulatedAnnealing.solveProblem();
        assertEquals(simpleProblem.getTargetNumber(), simpleProblem.getCurrentNumber(), SOLUTION_DELTA);

        simpleProblem.setTargetNumber(-3);
        simulatedAnnealing.solveProblem();
        assertEquals(simpleProblem.getTargetNumber(), simpleProblem.getCurrentNumber(), SOLUTION_DELTA);

        simpleProblem.setTargetNumber(0);
        simulatedAnnealing.solveProblem();
        assertEquals(simpleProblem.getTargetNumber(), simpleProblem.getCurrentNumber(), SOLUTION_DELTA);
    }

    public void testComplexSimulatedAnnealing() {
        simulatedAnnealing = new SimulatedAnnealing(complexProblem);
        simulatedAnnealing.solveProblem();
        assertEquals(0, complexProblem.getSolutionValue(), SOLUTION_DELTA);
    }
}
