package com.rich.budgetapi.api.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryModel {

    private Long id;
    private String description;
    private String color;
    private Boolean inactive;
}
