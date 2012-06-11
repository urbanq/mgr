package pl.edu.agh.service.reason;

import pl.edu.agh.domain.Department;

import java.util.List;

/**
 * @author mateusz
 * @date 11.06.12
 */
public class DepartmentsReason extends Reason<List<Department>> {
    public DepartmentsReason(List<Department> required) {
        super(required);
    }
}
