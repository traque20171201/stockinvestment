package traque.com.stockinvestment.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import traque.com.stockinvestment.entity.PriceFluctuate;

public interface PriceFluctuateRepository extends CrudRepository<PriceFluctuate, Integer> {

	@Query(nativeQuery=true)
	List<PriceFluctuate> findPriceFluctuate(@Param("exchange") Integer exchange,
			@Param("stock") String stock,
			@Param("date_from") Date date_from,
			@Param("date_to") Date date_to);
}
