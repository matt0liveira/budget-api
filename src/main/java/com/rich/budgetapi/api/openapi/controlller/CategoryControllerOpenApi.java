package com.rich.budgetapi.api.openapi.controlller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.rich.budgetapi.api.model.CategoryModelWithUser;
import com.rich.budgetapi.api.model.input.CategoryInputModel;
import com.rich.budgetapi.domain.filter.CategoryFilter;

import io.swagger.v3.oas.annotations.Operation;

public interface CategoryControllerOpenApi {

    @Operation(summary = "List all categories")
    public ResponseEntity<List<CategoryModelWithUser>> toList(CategoryFilter filter);

    @Operation(summary = "Find a category by ID")
    public CategoryModelWithUser toFind(Long categoryId);

    @Operation(summary = "Find categories by description")
    public ResponseEntity<List<CategoryModelWithUser>> toFindByDescription(
            String description);

    @Operation(summary = "Add a new category")
    public ResponseEntity<CategoryModelWithUser> toAdd(CategoryInputModel categoryInput);

    @Operation(summary = "Update data category by ID")
    public ResponseEntity<CategoryModelWithUser> toUpdate(Long categoryId,
            CategoryInputModel categoryInput);

    @Operation(summary = "Remove a category by ID")
    public ResponseEntity<Void> toRemove(Long categoryId);

    @Operation(summary = "Activate a category by ID")
    public ResponseEntity<Void> toActivate(Long categoryId);

    @Operation(summary = "Inactivate a category by ID")
    public ResponseEntity<Void> toInactivate(Long categoryId);
}
