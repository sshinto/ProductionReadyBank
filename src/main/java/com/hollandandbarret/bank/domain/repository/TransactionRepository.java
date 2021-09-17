package com.hollandandbarret.bank.domain.repository;

import com.hollandandbarret.bank.domain.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * Created by Savitha Shinto.
 */
@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    @Query(value= "SELECT * FROM Transaction WHERE account_Number =?1  and transaction_Date >= ?2 and transaction_Date <= ?3 " +
            " order by transaction_id desc", nativeQuery = true)
    List<Transaction> findTransactionByAccountNumberAndDate(long accountNumber, LocalDateTime from, LocalDateTime to);

}
