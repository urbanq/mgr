package pl.edu.agh.simulated.annealing.example;

import java.text.MessageFormat;

public class Function {

    private double[] w;
    private double[] p;

    public Function(int n, double maxw, double maxp) {
        if (n <= 0)
            throw new IllegalArgumentException("N must be grater than 0");

        w = new double[n + 1];
        p = new double[n];

        for (int i = 0; i <= n; i++) {
            w[i] = (StrictMath.random() - 0.5) * 2.0 * maxw;
            if (i < n)
                p[i] = (StrictMath.random() - 0.5) * 2.0 * maxp;
        }
    }

    public Function(double[] w, double[] p) {
        this(w.length - 1, 5.0, 3.0);
        System.arraycopy(w, 0, this.w, 0, w.length);
        System.arraycopy(p, 0, this.p, 0, p.length);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("f(x) = ");
        res.append(format(getW(w.length - 1)));
        res.append("x^");
        res.append(format(getP(w.length - 2)));

        for (int i = w.length - 2; i > 0; i--) {
            if (getW(i) < 0.0) {
                res.append(" - ");
                res.append(format(-getW(i)));
            } else {
                res.append(" + ");
                res.append(format(getW(i)));
            }
            res.append("x^").append(format(getP(i - 1)));
        }

        if (getW(0) < 0.0) {
            res.append(" - ");
            res.append(format(-getW(0)));
        } else if (getW(0) > 0.0) {
            res.append(" + ");
            res.append(format(getW(0)));
        }
        return res.toString();
    }

    private static String format(double v) {
        return MessageFormat.format("{0,number,0.00}", v);
    }

    public double eval(double x) {
        double y = w[deg()];
        for (int i = deg() - 1; i >= 0; i--)
        y = y * x + w[i];

        return y;
    }

    public int deg() {
        return w.length - 1;
    }

    public double getW(int i) {
        return w[i];
    }

    public double getP(int i) {
        return p[i];
    }

    public void setW(int i, double x) {
        w[i] = x;
    }

    public void setP(int i, double x) {
        p[i] = x;
    }

    public Function copy() {
        return new Function(w, p);
    }
}
