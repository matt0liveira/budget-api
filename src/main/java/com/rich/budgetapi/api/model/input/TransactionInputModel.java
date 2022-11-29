package com.rich.budgetapi.api.model.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TransactionInputModel {

    @NotNull
    @Positive
    private BigDecimal value;

    @NotBlank
    private String type;

    @NotNull
    @Valid
    private CategoryRefInputModel category;

    @NotBlank
    private String description;

    @NotBlank
    private String date;
}