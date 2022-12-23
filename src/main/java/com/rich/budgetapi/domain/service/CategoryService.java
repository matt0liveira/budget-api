package com.rich.budgetapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.rich.budgetapi.domain.exception.EntityInUseException;
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
        try {
            categoryRepository.deleteById(categoryId);
            categoryRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException("Register in use by others entities");
        }
    }

    @Transactional
    public void toActivate(Long categoryId) {
        Category category = findOrFail(categoryId);

        category.activate();
    }

    @Transactional
    public void toInactivate(Long categoryId) {
        Category category = findOrFail(categoryId);

        category.inactivate();
    }

    public Category findOrFail(Long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new UserNotFoundException(categoryId));
    }
}
