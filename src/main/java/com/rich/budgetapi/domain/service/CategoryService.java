package com.rich.budgetapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rich.budgetapi.domain.exception.UserNotFoundException;
import com.rich.budgetapi.domain.model.Category;
import com.rich.budgetapi.domain.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public Category toSave(Category newCategory) {
        return categoryRepository.save(newCategory);
    }

    @Transactional
    public void toRemove(Long categoryId) {
        Category category = findOrFail(categoryId);

        categoryRepository.delete(category);
    }

    public Category findOrFail(Long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new UserNotFoundException(categoryId));
    }
}
