package pl.edu.agh.simulated.annealing;

public class MockSimpleSimulatedAnnealingProblem implements ISimulatedAnnealingProblem {

    private static final double NEGATIVE_FACTOR = 0.5;
    private static final double MAXIMUM_CHANGE = 1.0;

    private double targetNumber;
    private double currentNumber = Math.random() * 2 - 1;
    private double coppyOfSolution = currentNumber;

    public final double getSolutionValue() {
        return Math.abs(targetNumber - currentNumber);
    }

    public void randomChangeOfSolution(double currentTemperature, double maxTemperature) {
        currentNumber += calculateChange(MAXIMUM_CHANGE) * (currentTemperature / maxTemperature);
    }

    public void makeCopyOfSolution() {
        coppyOfSolution = currentNumber;
    }

    private static double calculateChange(double maximumChange) {
        return (Math.random() - NEGATIVE_FACTOR) * maximumChange;
    }

    public void restorePreviousSolution() {
        currentNumber = coppyOfSolution;
    }

    public final double getTargetNumber() {
        return targetNumber;
    }

    public void setTargetNumber(double targetNumber) {
        this.targetNumber = targetNumber;
    }

    public final double getCurrentNumber() {
        return currentNumber;
    }
}