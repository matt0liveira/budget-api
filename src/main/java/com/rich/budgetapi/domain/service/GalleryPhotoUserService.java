package com.rich.budgetapi.domain.service;

import java.io.InputStream;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rich.budgetapi.domain.exception.PhotoNotFoundException;
import com.rich.budgetapi.domain.model.PhotoUser;
import com.rich.budgetapi.domain.repository.UserRepository;
import com.rich.budgetapi.domain.service.PhotoStorageService.NewPhoto;

@Service
public class GalleryPhotoUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhotoStorageService photoStorageService;

    @Transactional
    public PhotoUser toSave(PhotoUser photo, InputStream dataPhoto) {
        Long userId = photo.getUser().getId();
        String newFileName = photoStorageService.generateFileName(photo.getFileName());
        String fileNameCurrent = null;

        Optional<PhotoUser> photoCurrent = userRepository.findPhotoById(userId);

        if (photoCurrent.isPresent()) {
            userRepository.delete(photoCurrent.get());
            fileNameCurrent = photoCurrent.get().getFileName();
        }

        photo.setFileName(newFileName);
        photo = userRepository.save(photo);
        userRepository.flush();

        NewPhoto newPhoto = NewPhoto
                .builder()
                .fileName(photo.getFileName())
                .contentType(photo.getContentType())
                .inputStream(dataPhoto)
                .build();

        photoStorageService.toUpdate(fileNameCurrent, newPhoto);

        return photo;
    }

    @Transactional
    public void toRemove(Long userId) {
        PhotoUser photo = findOrFail(userId);

        userRepository.delete(photo);
        userRepository.flush();

        photoStorageService.remove(photo.getFileName());
    }

    public PhotoUser findOrFail(Long userId) {
        return userRepository
                .findPhotoById(userId)
                .orElseThrow(() -> new PhotoNotFoundException(userId));
    }
}
