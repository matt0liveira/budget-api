package com.rich.budgetapi.domain.filter;

import com.rich.budgetapi.domain.model.enums.TypeTransaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TotalTransactionsWithoutDateFilter {

    private TypeTransaction type;

    private Long userId;

    private Long categoryId;
}
