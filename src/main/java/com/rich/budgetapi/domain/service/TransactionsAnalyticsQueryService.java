package com.rich.budgetapi.domain.service;

import java.util.List;

import com.rich.budgetapi.domain.filter.TransactionsAnalyticsFilter;
import com.rich.budgetapi.domain.model.dto.TotalTransactions;
import com.rich.budgetapi.domain.model.dto.TotalTransactionsByDate;

public interface TransactionsAnalyticsQueryService {
    List<TotalTransactionsByDate> queryTotalTransactionsByDate(TransactionsAnalyticsFilter filter);

    List<TotalTransactions> queryTotalTransactions();
}
