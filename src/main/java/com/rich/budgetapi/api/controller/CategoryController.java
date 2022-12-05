package com.rich.budgetapi.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rich.budgetapi.api.assembler.categoryAssembler.CategoryInputModelDisassembler;
import com.rich.budgetapi.api.assembler.categoryAssembler.CategoryModelAssembler;
import com.rich.budgetapi.api.model.CategoryModel;
import com.rich.budgetapi.api.model.input.CategoryInputModel;
import com.rich.budgetapi.api.utils.ResourceUriHelper;
import com.rich.budgetapi.domain.model.Category;
import com.rich.budgetapi.domain.model.User;
import com.rich.budgetapi.domain.repository.CategoryRepository;
import com.rich.budgetapi.domain.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryModelAssembler categoryModelAssembler;

    @Autowired
    private CategoryInputModelDisassembler categoryInputDisassembler;

    @GetMapping
    public ResponseEntity<List<CategoryModel>> toList() {
        return ResponseEntity.ok().body(categoryModelAssembler.toCollectionModel(categoryRepository.findAll()));
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryModel> toFind(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(categoryModelAssembler.toModel(categoryService.findOrFail(categoryId)));
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<CategoryModel>> toFindByUser(@PathVariable Long userId) {
        return ResponseEntity.ok()
                .body(categoryModelAssembler.toCollectionModel(categoryRepository.findByUserId(userId)));
    }

    @GetMapping("/by-description")
    public ResponseEntity<List<CategoryModel>> toFindByDescription(@RequestParam(required = true) String description) {
        List<Category> categories = categoryRepository.findByDescriptionLike("%" + description + "%");

        return ResponseEntity.ok().body(categoryModelAssembler.toCollectionModel(categories));
    }

    @PostMapping
    public ResponseEntity<CategoryModel> toAdd(@RequestBody @Valid CategoryInputModel categoryInput) {
        Category newCategory = categoryInputDisassembler.toDomainObject(categoryInput);

        newCategory.setUser(new User());
        newCategory.getUser().setId(1L);

        newCategory = categoryService.toSave(newCategory);

        return ResponseEntity
                .created(ResourceUriHelper.addUriInResponseHeader(newCategory.getId()))
                .body(categoryModelAssembler.toModel(newCategory));
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryModel> toUpdate(@PathVariable Long categoryId,
            @RequestBody @Valid CategoryInputModel categoryInput) {
        Category categoryCurrent = categoryService.findOrFail(categoryId);

        categoryInputDisassembler.copyToDomainObject(categoryInput, categoryCurrent);

        categoryCurrent = categoryService.toSave(categoryCurrent);

        return ResponseEntity.ok().body(categoryModelAssembler.toModel(categoryCurrent));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> toRemove(@PathVariable Long categoryId) {
        categoryService.toRemove(categoryId);

        return ResponseEntity.noContent().build();
    }
}