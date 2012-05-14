package pl.edu.agh.dao;

import pl.edu.agh.domain.Department;

import java.util.List;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public interface DepartmentDao {
    Department get(String id);

    List<Department> getAll();
}
