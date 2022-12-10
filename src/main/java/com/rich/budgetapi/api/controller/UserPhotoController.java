package com.rich.budgetapi.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rich.budgetapi.api.assembler.photoUserAssembler.PhotoUserModelAssembler;
import com.rich.budgetapi.api.model.PhotoUserModel;
import com.rich.budgetapi.api.model.input.PhotoUserInputModel;
import com.rich.budgetapi.domain.model.PhotoUser;
import com.rich.budgetapi.domain.model.User;
import com.rich.budgetapi.domain.service.GalleryPhotoUserService;
import com.rich.budgetapi.domain.service.UserService;

@RestController
@RequestMapping("/users/{userId}/photo")
public class UserPhotoController {

    @Autowired
    private GalleryPhotoUserService galleryPhotoUser;

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoUserModelAssembler photoUserModelAssembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PhotoUserModel> toUpdate(@PathVariable Long userId,
            @Valid PhotoUserInputModel photoUserInput, @RequestPart(required = true) MultipartFile file)
            throws IOException {
        User user = userService.findOrFail(userId);

        PhotoUser photo = new PhotoUser();
        photo.setUser(user);
        photo.setContentType(file.getContentType());
        photo.setSize(file.getSize());
        photo.setFileName(file.getOriginalFilename());

        PhotoUser photoSaved = galleryPhotoUser.toSave(photo, file.getInputStream());

        return ResponseEntity
                .ok()
                .body(photoUserModelAssembler.toModel(photoSaved));
    }

    @GetMapping(produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE, MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<PhotoUserModel> toFind(@PathVariable Long userId,
            @RequestHeader(name = "accept") String acceptHeader) {
        if (acceptHeader.equals(MediaType.APPLICATION_JSON_VALUE)) {
            return ResponseEntity
                    .ok()
                    .body(photoUserModelAssembler.toModel(galleryPhotoUser.findOrFail(userId)));
        }

        return null;
    }
}