package com.rich.budgetapi.api.assembler.permissionAssembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rich.budgetapi.api.model.input.PermissionInputModel;
import com.rich.budgetapi.domain.model.Permission;

@Component
public class PermissionInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Permission toDomainObject(PermissionInputModel permissionInput) {
        return modelMapper.map(permissionInput, Permission.class);
    }

    public void copyToDomainObject(PermissionInputModel permissionInput, Permission permission) {
        modelMapper.map(permissionInput, permission);
    }
}
