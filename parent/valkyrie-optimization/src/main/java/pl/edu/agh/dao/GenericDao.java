package pl.edu.agh.dao;


/**
 * Generic Data Access Object interface. Contains standard CRUD operations
 * @param <T> domain object class
 * @param <K> key type class
 */
public interface GenericDao<T,K> extends ReadDao<T,K> {

    T create(T object);

    T update(T object);

    void delete(T object);
}
