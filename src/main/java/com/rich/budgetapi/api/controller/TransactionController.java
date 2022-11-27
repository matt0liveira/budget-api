package com.rich.budgetapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rich.budgetapi.api.assembler.transactionAssembler.TransactionModelAssembler;
import com.rich.budgetapi.api.model.TransactionModel;
import com.rich.budgetapi.domain.repository.TransactionRepository;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionModelAssembler transactionModelAssembler;

    @GetMapping
    public ResponseEntity<List<TransactionModel>> toList() {
        return ResponseEntity.ok().body(transactionModelAssembler.toCollectionModel(transactionRepository.findAll()));
    }
}
