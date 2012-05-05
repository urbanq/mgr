package pl.edu.agh.simulated.annealing.example;


import pl.edu.agh.simulated.annealing.ISimulatedAnnealingProblem;

import java.util.Random;

@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
public class FunctionFinder implements ISimulatedAnnealingProblem {

    private static final double MAXIMUM_CHANGE = 15.0;
    private static final double NEGATIVE_FACTOR = 0.5;

    private static final Random RANDOM = new Random();
    private double maximumChange = MAXIMUM_CHANGE;

    private long changesCt;

    private Function f;
    private Function fcopy;

    private double[] x;
    private double[] fx;

    public FunctionFinder(double[] x, double[] fx, int n, double maximumChange, double maxw, double maxp) {
        this.x = x;
        this.fx = fx;
        f = new Function(n, maxw, maxp);

        fcopy = f.copy();
        this.maximumChange = maximumChange;
    }

    public FunctionFinder(double[] x, double[] fx, int n, double maxw, double maxp) {
        this(x, fx, n, MAXIMUM_CHANGE, maxw, maxp);
    }

    @Override
    public double getSolutionValue() {
        double delta = 0.0;
        for (int i = 0; i < x.length; i++) {
            double v = x[i];
            double nfx = f.eval(v);
            delta += (fx[i] - nfx) * (fx[i] - nfx);
        }
        return StrictMath.sqrt(delta / (double) x.length);
    }

    @Override
    public void randomChangeOfSolution(double currentTemperature, double maxTemperature) {
        changesCt++;
        double delta = calculateChange(maximumChange) * (currentTemperature / maxTemperature);
        if (RANDOM.nextBoolean()) {
            int rand = RANDOM.nextInt(f.deg() + 1);
            f.setW(rand, f.getW(rand) + delta);
        } else {
            int rand = RANDOM.nextInt(f.deg());
            f.setP(rand, f.getP(rand) + delta);
        }
    }

    public long getChangesCt() {
        return changesCt;
    }

    @Override
    public void makeCopyOfSolution() {
        fcopy = f.copy();
    }

    @Override
    public void restorePreviousSolution() {
        f = fcopy.copy();
    }

    private static double calculateChange(double maximumChange) {
        return (RANDOM.nextDouble() - NEGATIVE_FACTOR) * maximumChange;
    }

    public Function getF() {
        return f;
    }
}