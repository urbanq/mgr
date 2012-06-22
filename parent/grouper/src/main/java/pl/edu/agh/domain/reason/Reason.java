package pl.edu.agh.domain.reason;

/**
 * @author mateusz
 * @date 11.06.12
 */
public abstract class Reason<T> implements Comparable<Reason<T>> {
    private final T required;

    public Reason(T required) {
        this.required = required;
    }

    public T required() {
        return required;
    }

    public Object getThis() {
        return this;
    }

    @Override
    public int compareTo(Reason<T> o) {
        return 0;
    }
}
