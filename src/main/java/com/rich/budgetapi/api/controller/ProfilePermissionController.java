package com.rich.budgetapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rich.budgetapi.api.assembler.permissionAssembler.PermissionModelAssembler;
import com.rich.budgetapi.api.model.PermissionModel;
import com.rich.budgetapi.domain.exception.PermissionNotFoundException;
import com.rich.budgetapi.domain.model.Permission;
import com.rich.budgetapi.domain.model.Profile;
import com.rich.budgetapi.domain.service.PermissionService;
import com.rich.budgetapi.domain.service.ProfileService;

@RestController
@RequestMapping("/profiles/{profileId}/permissions")
public class ProfilePermissionController {

    // @Autowired
    // private ProfileRepository profileRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @GetMapping
    public ResponseEntity<List<PermissionModel>> toList(@PathVariable Long profileId) {
        Profile profile = profileService.findOrfail(profileId);

        return ResponseEntity
                .ok()
                .body(permissionModelAssembler.toCollectionModel(profile.getPermissions()));
    }

    @GetMapping("/{permissionId}")
    public ResponseEntity<PermissionModel> toFind(@PathVariable Long profileId, @PathVariable Long permissionId) {
        Profile profile = profileService.findOrfail(profileId);
        Permission permission = permissionService.findOrFail(permissionId);

        if (!profile.getPermissions().contains(permission)) {
            throw new PermissionNotFoundException(permissionId);
        }

        return ResponseEntity
                .ok()
                .body(permissionModelAssembler.toModel(permission));
    }

    @PutMapping("/{permissionId}")
    public ResponseEntity<Void> toConnect(@PathVariable Long profileId, @PathVariable Long permissionId) {
        profileService.connectPermission(profileId, permissionId);

        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/{permissionId}")
    public ResponseEntity<Void> toDisassociate(@PathVariable Long profileId, @PathVariable Long permissionId) {
        profileService.disassociatePermission(profileId, permissionId);

        return ResponseEntity
                .noContent()
                .build();
    }

}