package pl.edu.agh.simulated.annealing;

public class SimulatedAnnealing {

    private ISimulatedAnnealingProblem problem;
    private IProgressListener progressListener;

    private double maxTemperature = 1000.0;
    private double maxInternalSteps = 100.0;
    private double temperatureDelta = 0.95;

    public SimulatedAnnealing(ISimulatedAnnealingProblem problem) {
        this.problem = problem;
    }

    public SimulatedAnnealing(ISimulatedAnnealingProblem problem, IProgressListener progressListener) {
        this(problem);
        this.progressListener = progressListener;
    }

    public SimulatedAnnealing(ISimulatedAnnealingProblem problem, double maxTemperature, double maxInternalSteps, double temperatureDelta) {
        this(problem);
        this.maxTemperature = maxTemperature;
        this.maxInternalSteps = maxInternalSteps;
        this.temperatureDelta = temperatureDelta;
    }

    public SimulatedAnnealing(ISimulatedAnnealingProblem problem, double maxTemperature, double maxInternalSteps, double temperatureDelta, IProgressListener progressListener) {
        this(problem, maxTemperature, maxInternalSteps, temperatureDelta);
        this.progressListener = progressListener;
    }

    @SuppressWarnings({"MethodWithMultipleLoops"})
    public void solveProblem() {
        double temperature = maxTemperature;
        double minSolutionValue = problem.getSolutionValue();
        do {
            int innerCounter = 0;
            do {
                problem.makeCopyOfSolution();
                problem.randomChangeOfSolution(temperature, maxTemperature);
                double currentSolutionValue = problem.getSolutionValue();

                if (currentSolutionValue < minSolutionValue)
                    minSolutionValue = currentSolutionValue;
                else {
                    if (shouldTryWorseSolution(currentSolutionValue, minSolutionValue, temperature))
                        minSolutionValue = currentSolutionValue;
                    else
                        problem.restorePreviousSolution();
                }

                innerCounter++;
            } while (innerCounter < (int) maxInternalSteps);
            temperature *= temperatureDelta;
            if (progressListener != null)
                progressListener.progress(1 - temperature / maxTemperature, temperature);
        } while (temperature > 0.01);
    }

    private static boolean shouldTryWorseSolution(double currentSolutionValue, double minSolutionValue, double temperature) {
        double p = StrictMath.exp(-1.0 * ((currentSolutionValue - minSolutionValue) / temperature));
        return StrictMath.random() > p;
    }
}