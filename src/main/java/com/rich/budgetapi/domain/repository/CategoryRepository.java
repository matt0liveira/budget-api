package com.rich.budgetapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rich.budgetapi.domain.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>, CategoryRepositoryQueries {
    List<Category> findByDescriptionLike(String description);
}
