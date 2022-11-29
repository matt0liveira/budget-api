package com.rich.budgetapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rich.budgetapi.domain.exception.TransactionNotFoundException;
import com.rich.budgetapi.domain.model.Category;
import com.rich.budgetapi.domain.model.Transaction;
import com.rich.budgetapi.domain.model.User;
import com.rich.budgetapi.domain.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Transactional
    public Transaction toSave(Transaction newTransaction) {
        return transactionRepository.save(newTransaction);
    }

    @Transactional
    public void toRemove(String transactionCode) {
        Transaction transaction = findOrFail(transactionCode);

        transactionRepository.delete(transaction);
    }

    public Transaction findOrFail(String code) {
        return transactionRepository.findByCode(code)
                .orElseThrow(() -> new TransactionNotFoundException(code));
    }

    public void validateTransaction(Transaction transaction) {
        Category category = categoryService.findOrFail(transaction.getCategory().getId());
        User user = userService.findOrFail(transaction.getUser().getId());

        transaction.setCategory(category);
        transaction.setUser(user);
    }

}