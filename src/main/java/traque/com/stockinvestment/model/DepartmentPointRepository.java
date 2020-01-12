package traque.com.stockinvestment.model;

import org.springframework.data.repository.CrudRepository;

import traque.com.stockinvestment.entity.DepartmentPoint;

public interface DepartmentPointRepository extends CrudRepository<DepartmentPoint, Integer> {

}
