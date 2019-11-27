package traque.com.stockinvestment.model;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import traque.com.stockinvestment.entity.Price;

public interface PriceRepository extends CrudRepository<Price, Integer> {

	@Query("SELECT p FROM Price p WHERE p.exchange = :exchange AND (:stock IS NULL OR p.stock = : stock) "
			+ "AND p.date >= :date_from AND p.date <= :date_to")
	public Page<Price> findPriceHistory(@Param("exchange") Integer exchange,
			@Param("stock") String stock,
			@Param("date_from") Date date_from,
			@Param("date_to") Date date_to,
			Pageable pageable);
}
