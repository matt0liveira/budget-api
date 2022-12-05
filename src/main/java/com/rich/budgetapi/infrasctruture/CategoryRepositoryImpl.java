package com.rich.budgetapi.infrasctruture;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;

import com.rich.budgetapi.domain.model.Category;
import com.rich.budgetapi.domain.repository.CategoryRepositoryQueries;

public class CategoryRepositoryImpl implements CategoryRepositoryQueries {

    @Autowired
    private EntityManager manager;

    @Override
    public List<Category> findByUserId(Long userId) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(Category.class);
        var root = query.from(Category.class);

        var selection = builder.construct(Category.class, builder.literal("*"));

        query.select(selection);
        query.where(builder.equal(builder.literal(userId), root.get("user")));

        return manager.createQuery(query).getResultList();
    }

}
