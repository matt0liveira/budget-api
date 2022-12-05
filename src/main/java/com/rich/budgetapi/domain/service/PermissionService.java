package com.rich.budgetapi.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rich.budgetapi.domain.exception.PermissionNotFoundException;
import com.rich.budgetapi.domain.model.Permission;
import com.rich.budgetapi.domain.repository.PermissionRepository;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Transactional
    public Permission toSave(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Transactional
    public void toRemove(Long permissionId) {
        Permission permission = findOrFail(permissionId);

        permissionRepository.delete(permission);
    }

    public Permission findOrFail(Long permissionId) {
        return permissionRepository
                .findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException(permissionId));
    }
}
