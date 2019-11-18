package traque.com.stockinvestment.model;

import org.springframework.data.repository.CrudRepository;

import traque.com.stockinvestment.entity.Price;

public interface PriceRepository extends CrudRepository<Price, Integer> {

}
