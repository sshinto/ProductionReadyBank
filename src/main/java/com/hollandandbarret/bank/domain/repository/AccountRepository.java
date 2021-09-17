package com.hollandandbarret.bank.domain.repository;


import com.hollandandbarret.bank.domain.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Savitha Shinto.
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    @Query(value= "SELECT * FROM Account WHERE customer_Id =?1 " +
            " order by created_timestamp desc", nativeQuery = true)
    List<Account> findAccountByCustomerNumber(long customerNumber);

}
