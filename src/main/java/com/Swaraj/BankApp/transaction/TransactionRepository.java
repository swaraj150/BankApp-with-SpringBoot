package com.Swaraj.BankApp.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    Optional<Transaction> findBySender(Integer account);
    Optional<Transaction> findByReceiver(Integer account);
}
