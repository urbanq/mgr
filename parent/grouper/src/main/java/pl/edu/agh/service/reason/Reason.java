package pl.edu.agh.service.reason;

/**
 * @author mateusz
 * @date 11.06.12
 */
public abstract class Reason<T> {
    private final T required;

    public Reason(T required) {
        this.required = required;
    }

    public T required() {
        return required;
    }
}
