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
import com.rich.budgetapi.api.model.input.CategoryInputModel;
import com.rich.budgetapi.api.utils.ResourceUriHelper;
import com.rich.budgetapi.domain.model.Category;
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
    private CategoryInputModelDisassembler categoryInputDisassembler;

    @GetMapping
    public ResponseEntity<List<Category>> toList() {
        return ResponseEntity.ok().body(categoryRepository.findAll());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> toFind(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(categoryService.findOrFail(categoryId));
    }

    @GetMapping("/by-description")
    public ResponseEntity<List<Category>> toFindByDescription(@RequestParam(required = true) String description) {
        List<Category> categories = categoryRepository.findByDescriptionLike("%" + description + "%");

        return ResponseEntity.ok().body(categories);
    }

    @PostMapping
    public ResponseEntity<Category> toSave(@RequestBody @Valid CategoryInputModel categoryInput) {
        Category newCategory = categoryInputDisassembler.toDomainObject(categoryInput);

        newCategory = categoryService.toSave(newCategory);

        return ResponseEntity
                .created(ResourceUriHelper.addUriInResponseHeader(newCategory.getId()))
                .body(newCategory);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> toUpdate(@PathVariable Long categoryId,
            @RequestBody @Valid CategoryInputModel categoryInput) {
        Category categoryCurrent = categoryService.findOrFail(categoryId);

        categoryInputDisassembler.copyToDomainObject(categoryInput, categoryCurrent);

        categoryCurrent = categoryService.toSave(categoryCurrent);

        return ResponseEntity.ok().body(categoryCurrent);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> toRemove(@PathVariable Long categoryId) {
        categoryService.toRemove(categoryId);

        return ResponseEntity.noContent().build();
    }
}