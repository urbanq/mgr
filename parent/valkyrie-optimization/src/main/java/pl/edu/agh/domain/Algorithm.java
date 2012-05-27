package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 27.05.12
 */
public enum Algorithm {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    I,
    J,
    K,
    L,
    M,
    N,
    O,
    P,
    R,
    S,
    T,
    U,
    V,
    W,
    X,
    Y,
    Z;

    public static final Algorithm valueOf(char code) {
        for(Algorithm algorithm : values()) {
            if(algorithm.name().equalsIgnoreCase(String.valueOf(code))) {
                return algorithm;
            }
        }
        throw new IllegalArgumentException("code = " + code);
    }
}
