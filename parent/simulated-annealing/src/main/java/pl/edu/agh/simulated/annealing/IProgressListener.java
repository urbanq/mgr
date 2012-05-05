package pl.edu.agh.simulated.annealing;

public interface IProgressListener {
    void progress(double percent, double temperature);
}
