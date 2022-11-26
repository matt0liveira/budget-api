package com.rich.budgetapi.api.model;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserModel {

    private Long id;
    private String name;
    private String email;
    private BigDecimal balance;
}
