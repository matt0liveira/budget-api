package com.rich.budgetapi.api.model.input;

import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import com.rich.budgetapi.core.validation.FileContentType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PhotoUserInputModel {

    @NotNull
    @FileContentType(allowed = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
    private MultipartFile file;
}
