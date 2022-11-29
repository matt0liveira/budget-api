package com.rich.budgetapi.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rich.budgetapi.api.assembler.transactionAssembler.TransactionInputModelDisassembler;
import com.rich.budgetapi.api.assembler.transactionAssembler.TransactionModelAssembler;
import com.rich.budgetapi.api.model.TransactionModel;
import com.rich.budgetapi.api.model.input.TransactionInputModel;
import com.rich.budgetapi.api.utils.ResourceUriHelper;
import com.rich.budgetapi.core.validation.ValidationException;
import com.rich.budgetapi.domain.model.Transaction;
import com.rich.budgetapi.domain.model.User;
import com.rich.budgetapi.domain.model.enums.TypeTransaction;
import com.rich.budgetapi.domain.repository.TransactionRepository;
import com.rich.budgetapi.domain.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionModelAssembler transactionModelAssembler;

    @Autowired
    private TransactionInputModelDisassembler transactionInputModelDisassembler;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SmartValidator validator;

    @GetMapping
    public ResponseEntity<List<TransactionModel>> toList() {
        return ResponseEntity.ok().body(transactionModelAssembler.toCollectionModel(transactionRepository.findAll()));
    }

    @GetMapping("/{transactionCode}")
    public ResponseEntity<TransactionModel> toFind(@PathVariable String transactionCode) {
        return ResponseEntity.ok()
                .body(transactionModelAssembler.toModel(transactionService.findOrFail(transactionCode)));
    }

    @PostMapping
    public ResponseEntity<TransactionModel> toAdd(@RequestBody @Valid TransactionInputModel transactionInput) {
        Transaction newTransaction = transactionInputModelDisassembler.toDomainObject(transactionInput);

        newTransaction.setUser(new User());
        newTransaction.getUser().setId(1L);

        verifyTypeTransaction(newTransaction);

        newTransaction = transactionService.toSave(newTransaction);

        return ResponseEntity.created(ResourceUriHelper.addUriInResponseHeader(newTransaction.getCode()))
                .body(transactionModelAssembler.toModel(newTransaction));
    }

    @PatchMapping("/{transactionCode}")
    public ResponseEntity<TransactionModel> toPartialUpdate(@PathVariable String transactionCode,
            @RequestBody Map<String, Object> fields, HttpServletRequest request) {
        Transaction transactionCurrent = transactionService.findOrFail(transactionCode);

        this.merge(fields, transactionCurrent, request);
        validate(transactionCurrent, "transaction");

        if (fields.containsKey("value")) {
            verifyTypeTransaction(transactionCurrent);
        }

        transactionCurrent = transactionService.toSave(transactionCurrent);

        return ResponseEntity.ok().body(transactionModelAssembler.toModel(transactionCurrent));
    }

    @DeleteMapping("/{transactionCode}")
    public ResponseEntity<Void> toRemove(@PathVariable String transactionCode) {
        transactionService.toRemove(transactionCode);

        return ResponseEntity.noContent().build();
    }

    private void verifyTypeTransaction(Transaction transaction) {
        BigDecimal balanceUser = transaction.getUser().getBalance();

        if (TypeTransaction.EXPENSE.equals(transaction.getType())) {
            transaction.getUser().setBalance(
                    balanceUser.subtract(transaction.getValue()));
        } else {
            transaction.getUser()
                    .setBalance(balanceUser.add(transaction.getValue()));
        }
    }

    private void validate(Transaction transactionCurrent, String objName) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(transactionCurrent, objName);

        validator.validate(transactionCurrent, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
    }

    private void merge(Map<String, Object> fields, Transaction transactionCurrent, HttpServletRequest request) {
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
            Transaction transaction = objectMapper.convertValue(fields, Transaction.class);

            fields.forEach((name, value) -> {
                Field field = ReflectionUtils.findField(Transaction.class, name);
                if (field != null) {
                    field.setAccessible(true);
                }

                Object newValue = ReflectionUtils.getField(field, transaction);

                ReflectionUtils.setField(field, transactionCurrent, newValue);
            });
        } catch (IllegalArgumentException e) {
            Throwable cause = ExceptionUtils.getRootCause(e);

            throw new HttpMessageNotReadableException(e.getMessage(), cause, serverHttpRequest);
        }

    }
}