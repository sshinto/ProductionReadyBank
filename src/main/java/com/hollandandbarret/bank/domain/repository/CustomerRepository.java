package com.hollandandbarret.bank.domain.repository;
import com.hollandandbarret.bank.domain.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Savitha Shinto.
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
