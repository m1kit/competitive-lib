package jp.llv.atcoder.lib.math;

public class Eps {

    public static final double DEFAULT_EPS = 1e-8;

    private final double abs, rel;

    public Eps(double abs, double rel) {
        this.abs = abs;
        this.rel = rel;
    }

    public Eps(double eps) {
        this(eps, eps);
    }

    public Eps() {
        this(DEFAULT_EPS);
    }

    public int sgn(double v) {
        return v < -abs ? -1 : v > abs ? 1 : 0;
    }

    public boolean equals(double ans, double act) {
        if (Math.abs(act - ans) < abs) {
            return true;
        }
        return ans != 0 && Math.abs(1 - (act / ans)) < rel;
    }
}
