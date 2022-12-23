package com.rich.budgetapi.infrasctruture.spec;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.rich.budgetapi.domain.filter.CategoryFilter;
import com.rich.budgetapi.domain.model.Category;

public class CategorySpec {

    public static Specification<Category> usingFilter(CategoryFilter filter) {
        return (root, query, builder) -> {
            if (Category.class.equals(query.getResultType())) {
                root.fetch("user");
            }

            var predicates = new ArrayList<Predicate>();

            if (filter.getUserId() != null) {
                predicates.add(builder.equal(root.get("user"), filter.getUserId()));
            }

            if (filter.getOnlyActive() != null && filter.getOnlyActive()) {
                predicates.add(builder.equal(root.get("inactive"), Boolean.FALSE));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
