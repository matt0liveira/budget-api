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
import org.springframework.web.bind.annotation.RestController;

import com.rich.budgetapi.api.assembler.permissionAssembler.PermissionInputModelDisassembler;
import com.rich.budgetapi.api.assembler.permissionAssembler.PermissionModelAssembler;
import com.rich.budgetapi.api.model.PermissionModel;
import com.rich.budgetapi.api.model.input.PermissionInputModel;
import com.rich.budgetapi.api.utils.ResourceUriHelper;
import com.rich.budgetapi.domain.model.Permission;
import com.rich.budgetapi.domain.repository.PermissionRepository;
import com.rich.budgetapi.domain.service.PermissionService;

@RestController
@RequestMapping("/permissions")
public class PermissionController {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @Autowired
    private PermissionInputModelDisassembler permissionInputModelDisassembler;

    @GetMapping
    public ResponseEntity<List<PermissionModel>> toList() {
        return ResponseEntity.ok().body(permissionModelAssembler.toCollectionModel(permissionRepository.findAll()));
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<PermissionModel> toFind(@PathVariable Long permissionId) {
        return ResponseEntity.ok().body(permissionModelAssembler.toModel(permissionService.findOrFail(permissionId)));
    }

    @PostMapping
    public ResponseEntity<PermissionModel> toAdd(@RequestBody @Valid PermissionInputModel permissionInput) {
        Permission newPermission = permissionInputModelDisassembler.toDomainObject(permissionInput);

        newPermission = permissionService.toSave(newPermission);

        return ResponseEntity
                .created(ResourceUriHelper.addUriInResponseHeader(newPermission.getId()))
                .body(permissionModelAssembler.toModel(newPermission));
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<PermissionModel> toUpdate(@PathVariable Long permissionId,
            @RequestBody @Valid PermissionInputModel permissionInput) {
        Permission permissionCurrent = permissionService.findOrFail(permissionId);

        permissionInputModelDisassembler.copyToDomainObject(permissionInput, permissionCurrent);

        permissionCurrent = permissionService.toSave(permissionCurrent);

        return ResponseEntity
                .ok()
                .body(permissionModelAssembler.toModel(permissionCurrent));
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> toRemove(@PathVariable Long permissionId) {
        permissionService.toRemove(permissionId);

        return ResponseEntity
                .noContent()
                .build();
    }

}
