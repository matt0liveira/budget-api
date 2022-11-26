package com.rich.budgetapi.api.assembler.categoryAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rich.budgetapi.api.model.input.CategoryInputModel;
import com.rich.budgetapi.domain.model.Category;

@Component
public class CategoryInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Category toDomainObject(CategoryInputModel categoryInput) {
        return modelMapper.map(categoryInput, Category.class);
    }

    public void copyToDomainObject(CategoryInputModel categoryInput, Category category) {
        modelMapper.map(categoryInput, category);
    }
}
