package com.rich.budgetapi.domain.model.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TotalTransactions {

    private Long totalTransations;
    private BigDecimal totalValue;
}
