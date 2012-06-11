package pl.edu.agh.domain;


/**
 * User: mateusz
 * Date: 27.05.12
 */
public enum Condition {
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
    Q,
    R,
    S,
    T,
    U,
    V,
    W,
    X,
    Y,
    Z;

    public static final Condition valueOf(char code) {
        for(Condition condition : values()) {
            if(condition.name().equalsIgnoreCase(String.valueOf(code))) {
                return condition;
            }
        }
        throw new IllegalArgumentException("code = " + code);
    }
}
