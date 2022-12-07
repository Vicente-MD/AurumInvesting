package com.aurum.aurumapp.transaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.aurum.aurumapp.transaction.model.Transaction;
import com.aurum.aurumapp.wallet.model.Wallet;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public Optional<List<Transaction>> findByWallet(Wallet wallet);
    @Modifying
    @Query(value="UPDATE transaction set transaction.status_id = ?2 WHERE transaction.id = ?1", nativeQuery=true)
    void setStatus(Long id,  Long status_id);
}
