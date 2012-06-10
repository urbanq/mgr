package pl.edu.agh.dao;

import pl.edu.agh.domain.Department;
import pl.edu.agh.domain.JGP;

import java.util.List;

/**
 * User: mateusz
 * Date: 14.05.12
 */
public interface DepartmentDao extends ReadDao<Department, String> {

    List<Department> getByJGP(JGP jgp);
}
