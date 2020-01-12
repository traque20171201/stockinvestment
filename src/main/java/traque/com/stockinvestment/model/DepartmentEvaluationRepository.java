package traque.com.stockinvestment.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import traque.com.stockinvestment.entity.DepartmentEvaluation;

public interface DepartmentEvaluationRepository extends CrudRepository<DepartmentEvaluation, Integer>{

	List<DepartmentEvaluation> findByDepartmentIdAndYear(Integer departmentId, Integer year);
}
