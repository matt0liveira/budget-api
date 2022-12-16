package com.rich.budgetapi.api.model;

import java.math.BigDecimal;
import java.sql.Date;

import com.rich.budgetapi.domain.model.enums.TypeTransaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionModel {

    private String code;
    private BigDecimal value;
    private String description;
    private TypeTransaction typeTransaction;
    private Date date;
    private UserModel user;
    private CategoryModel category;
}