package traque.com.stockinvestment.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import traque.com.stockinvestment.entity.Department;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {

	List<Department> findByName(String name);
}
