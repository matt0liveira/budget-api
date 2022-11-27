package com.rich.budgetapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rich.budgetapi.domain.exception.TransactionNotFoundException;
import com.rich.budgetapi.domain.model.Transaction;
import com.rich.budgetapi.domain.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction findOrFail(String code) {
        return transactionRepository.findByCode(code)
                .orElseThrow(() -> new TransactionNotFoundException(code));
    }

}