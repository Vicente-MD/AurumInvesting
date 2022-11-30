package com.aurum.aurumapp.transaction.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aurum.aurumapp.transaction.model.Transaction;
import com.aurum.aurumapp.wallet.model.Wallet;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public Optional<List<Transaction>> findByWallet(Wallet wallet);
}
