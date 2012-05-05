package pl.edu.agh.simulated.annealing.example;

import pl.edu.agh.simulated.annealing.IProgressListener;
import pl.edu.agh.simulated.annealing.SimulatedAnnealing;

import java.text.MessageFormat;

/**
 * Program SampleSaProblem poszukuje postaci wcześniej zdefiniowanej funkcji na podstawie znanych wektorów x i fx.
 * Być może nie są one zaimplementowane optymalnie, ale służą jedynie jako poglądowe przykłady
 */
@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
public class SampleSaProblem {

    public static void main(String[] args) {

        Function function = new Function(
                new double[]{0.3, 0.75, 1.3, 2.2},
                new double[]{1.3, 1.75, 2.3}
        );

        double[] x = new double[400];
        double[] fx = new double[400];

        int i = 0;
        for (double v = 0.0; v < 100.0; v += 0.25) {
            x[i] = v;
            fx[i] = function.eval(v);
            i++;
        }

        System.out.println("+------------------------------------------------------------------------------");
        System.out.println(MessageFormat.format("| Original f(x) = {0}", function));
        System.out.println("+------------------------------------------------------------------------------");

        final FunctionFinder functionFinder = new FunctionFinder(x, fx, 3, 1.0, 5.0, 3.0);
        double svb = functionFinder.getSolutionValue();
        System.out.println(MessageFormat.format("| Sqr. diff. before: {0}", svb));
        System.out.println(MessageFormat.format("| F: {0}", functionFinder.getF()));

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(functionFinder, 100000000.0, 1000000.0, 0.99, new IProgressListener() {

            private int ct;

            @Override
            public void progress(double percent, double temperature) {
                ct++;
                if (ct > 9) {
                    System.out.println(MessageFormat.format("| dT: {0,number,00}%, SV: {1}, T: {2}, Ch: {3}", percent * 100.0, functionFinder.getSolutionValue(), temperature, functionFinder.getChangesCt()));
                    ct = 0;
                }
            }
        });

        long timeStart = System.currentTimeMillis();
        simulatedAnnealing.solveProblem();

        double sva = functionFinder.getSolutionValue();
        System.out.println(MessageFormat.format("| Sqr. diff. after : {0}", sva));
        System.out.println(MessageFormat.format("| F: {0}", functionFinder.getF()));
        System.out.println(MessageFormat.format("| Time: {0} sec. Result: {1}", (System.currentTimeMillis() - timeStart) / 1000L, sva / svb));
    }
}
