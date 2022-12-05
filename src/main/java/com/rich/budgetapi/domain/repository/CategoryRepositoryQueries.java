package com.rich.budgetapi.domain.repository;

import java.util.List;

import com.rich.budgetapi.domain.model.Category;

public interface CategoryRepositoryQueries {

    List<Category> findByUserId(Long userId);

}
