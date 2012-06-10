package pl.edu.agh.dao;

import java.util.List;

public interface FilterDao<T, F extends GenericFilter<T>> {
    public List<T> getList(F filter);
}
