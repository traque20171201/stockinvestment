package traque.com.stockinvestment.model;

import org.springframework.data.repository.CrudRepository;

import traque.com.stockinvestment.entity.Staff;

public interface StaffRepository extends CrudRepository<Staff, Integer> {

}
