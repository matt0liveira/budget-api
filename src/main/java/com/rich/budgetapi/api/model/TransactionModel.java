package com.rich.budgetapi.api.model;

import java.sql.Date;

import com.rich.budgetapi.domain.model.Category;
import com.rich.budgetapi.domain.model.enums.TypeTransaction;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionModel {

    private String code;
    private UserModel user;
    private TypeTransaction typeTransaction;
    private Category category;
    private String description;
    private Date date;
}
