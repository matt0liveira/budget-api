package com.rich.budgetapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rich.budgetapi.domain.filter.TransactionsAnalyticsFilter;
import com.rich.budgetapi.domain.model.dto.TotalTransactionsByDate;
import com.rich.budgetapi.domain.service.TransactionsAnalyticsQueryService;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Autowired
    private TransactionsAnalyticsQueryService transactionsAnalyticsService;

    @GetMapping("/transactions-analytics")
    public ResponseEntity<List<TotalTransactionsByDate>> queryTotalTransactionsByDate(
            TransactionsAnalyticsFilter filter) {
        return ResponseEntity.ok().body(transactionsAnalyticsService.queryTotalTransactionsByDate(filter));
    }

}