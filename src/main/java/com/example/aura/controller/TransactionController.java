package com.example.aura.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.aura.model.Transaction;
import com.example.aura.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public String processTransaction(@RequestBody Transaction transaction){
        transactionService.processTransaction(transaction);

        if(transactionService.checkIfFraudulent(transaction)){
            return "Transaction flagged as potential fraud. Investigation needed";
        }
        return "Transaction processed successfully";
    }

    @GetMapping("/flagged")
    public List<Transaction> getFlaggedTransactions(){
        //fetch for all transactions flagged as fraudulent
        return transactionService.getFlaggedTransactions();
    }

    @GetMapping("/user/{userId}")
    public List<Transaction> getUserTransactions(@PathVariable String userId){
        //fetch all transactions of a specific user
            return transactionService.getUserTransactions(userId);
    }
}
