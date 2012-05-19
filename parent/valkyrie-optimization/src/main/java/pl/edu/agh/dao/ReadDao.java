package pl.edu.agh.dao;

import java.util.List;

/**
 * User: mateusz
 * Date: 19.05.12
 */
public interface ReadDao<T,K> {
    T get(K id);

    List<T> getAll();
}
