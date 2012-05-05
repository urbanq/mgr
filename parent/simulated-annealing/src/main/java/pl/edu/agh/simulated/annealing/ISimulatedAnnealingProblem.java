package pl.edu.agh.simulated.annealing;

/**
 * Created with IntelliJ IDEA.
 * User: mateusz
 * Date: 03.05.12
 * Time: 13:17
 * To change this template use File | Settings | File Templates.
 */
public interface ISimulatedAnnealingProblem {

    double getSolutionValue();

    void randomChangeOfSolution(double currentTemperature, double maxTemperature);

    void makeCopyOfSolution();

    void restorePreviousSolution();
}
