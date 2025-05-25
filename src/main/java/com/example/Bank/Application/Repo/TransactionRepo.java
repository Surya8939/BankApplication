package com.example.Bank.Application.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.Bank.Application.Entity.AccountDetails;
import com.example.Bank.Application.Entity.Transaction;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE t.account.id = :id")
    List<Transaction> getAllTransation(@Param("id") Long id);

    Optional<Transaction> findTopByAccountOrderByIdDesc(AccountDetails account);

}
