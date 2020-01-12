package traque.com.stockinvestment.model;

import org.springframework.data.repository.CrudRepository;

import traque.com.stockinvestment.entity.DepartmentTask;

public interface DepartmentTaskRepository extends CrudRepository<DepartmentTask, Integer> {

}
