package com.rich.budgetapi.domain.filter;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.rich.budgetapi.domain.model.enums.TypeTransaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionsAnalyticsFilter {

    private Long userId;

    private TypeTransaction type;

    @DateTimeFormat(iso = ISO.DATE)
    private Date dateTransactionInitial;

    @DateTimeFormat(iso = ISO.DATE)
    private Date dateTransactionFinal;
}
