package pl.edu.agh.simulated.annealing;


public class MockComplexSimulatedAnnealingProblem implements ISimulatedAnnealingProblem {

    private static final double NEGATIVE_FACTOR = 0.5;
    private static final double MAXIMUM_CHANGE = 1.0;

    private static final double[][] input = {
            {4.0, 7.0, 6.0},
            {0.0, 8.0, 7.0},
            {6.0, 6.0, 1.0},
            {3.0, 9.0, 8.0},
            {1.0, 9.0, 3.0}};

    private static final double[] output = {18.600, 11.200, 8.600, 19.900, -0.700};

    //From spreadsheet: 2.3; -1.4; 3.2
    private final double[] params = {0.0, 0.0, 0.0};
    private final double[] paramsCopy = {0.0, 0.0, 0.0};

    public final double getSolutionValue() {
        return calculateSquerError();
    }

    public void randomChangeOfSolution(double currentTemperature, double maxTemperature) {
        int index = (int) StrictMath.round(StrictMath.random() * 2.0);
        params[index] += calculateChange(MAXIMUM_CHANGE) * (currentTemperature / maxTemperature);
    }

    private static double calculateChange(double maximumChange) {
        return (StrictMath.random() - NEGATIVE_FACTOR) * maximumChange;
    }

    public void restorePreviousSolution() {
        copyParamsArray(paramsCopy, params);
    }

    public void makeCopyOfSolution() {
        copyParamsArray(params, paramsCopy);
    }

    private void copyParamsArray(double[] source, double[] destination) {
        System.arraycopy(source, 0, destination, 0, params.length);
    }

    private double calculateSquerError() {
        double sum = 0.0;
        for (int i = 0; i < 5; i++)
            sum += calculateFunctionSqrError(i);

        if (sum > 0.0)
            return Math.sqrt(sum);
        else
            return 0.0;
    }

    private double calculateFunctionSqrError(int i) {
        return StrictMath.pow(calculateFunctionError(i), 2.0);
    }

    private double calculateFunctionError(int i) {
        return output[i] - calculateFunction(i);
    }

    private double calculateFunction(int i) {
        return calculateOutput(i, 0) + calculateOutput(i, 1) + calculateOutput(i, 2);
    }

    private double calculateOutput(int i, int item) {
        return params[item] * input[i][item];
    }

    public final double[] getParams() {
        return params;
    }
}