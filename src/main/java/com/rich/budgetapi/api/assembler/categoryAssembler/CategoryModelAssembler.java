package com.rich.budgetapi.api.assembler.categoryAssembler;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rich.budgetapi.api.model.CategoryModel;
import com.rich.budgetapi.domain.model.Category;

@Component
public class CategoryModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CategoryModel toModel(Category category) {
        return modelMapper.map(category, CategoryModel.class);
    }

    public List<CategoryModel> toCollectionModel(List<Category> categories) {
        List<CategoryModel> categoriesModel = new ArrayList<>();
        for (Category category : categories) {
            categoriesModel.add(toModel(category));
        }

        return categoriesModel;
    }
}
