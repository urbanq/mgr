package pl.edu.agh.domain;

/**
 * @author mateusz
 * @date 10.06.12
 */
public class JGPHospital {
    private JGP jgp;
    /**
     * liczba dni pobytu finansowana grupą
     */
    private int days;
    /*
     * wartość punktowa hospitalizacji < 2 dni
     */
    private Double underValue;
    /*
     * wartość punktowa osobodnia ponad ryczałt finansowany grupą
     */
    private Double overValue;

    public JGP getJgp() {
        return jgp;
    }

    public void setJgp(JGP jgp) {
        this.jgp = jgp;
    }

    /**
     * liczba dni pobytu finansowana grupą
     */
    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    /*
     * wartość punktowa hospitalizacji < 2 dni
     */
    public Double getUnderValue() {
        return underValue;
    }

    public void setUnderValue(Double underValue) {
        this.underValue = underValue;
    }

    /*
     * wartość punktowa osobodnia ponad ryczałt finansowany grupą
     */
    public Double getOverValue() {
        return overValue;
    }

    public void setOverValue(Double overValue) {
        this.overValue = overValue;
    }
}
