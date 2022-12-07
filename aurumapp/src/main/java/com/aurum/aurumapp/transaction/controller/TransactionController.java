package com.aurum.aurumapp.transaction.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aurum.aurumapp.transaction.model.Transaction;
import com.aurum.aurumapp.transaction.repository.TransactionRepository;
import com.aurum.aurumapp.wallet.model.Wallet;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionRepository transactionRepository;

    @GetMapping
    public ResponseEntity<?> listAllTransactions(){
        return ResponseEntity.ok(transactionRepository.findAll());
    }

    @GetMapping("/wallet")
    ResponseEntity<List<Transaction>> getTransactionByWallet(@RequestBody Wallet wallet){
        return ResponseEntity.ok(transactionRepository.findByWallet(wallet).get());
    }
}
