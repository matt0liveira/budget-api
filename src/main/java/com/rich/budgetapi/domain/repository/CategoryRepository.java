package com.rich.budgetapi.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rich.budgetapi.domain.model.Category;

@Repository
public interface CategoryRepository
        extends JpaRepository<Category, Long>, CategoryRepositoryQueries, JpaSpecificationExecutor<Category> {

    @Query("from Category c join fetch c.user")
    List<Category> findAll();

    @Query("from Category c where c.inactive = 0")
    List<Category> findOnlyActive();
}
