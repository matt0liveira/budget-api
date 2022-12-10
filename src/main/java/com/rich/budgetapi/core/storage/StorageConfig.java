package com.rich.budgetapi.core.storage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rich.budgetapi.domain.service.PhotoStorageService;
import com.rich.budgetapi.infrasctruture.service.storage.LocalPhotoStorageService;

@Configuration
public class StorageConfig {

    @Bean
    public PhotoStorageService photoStorageService() {
        return new LocalPhotoStorageService();
    }
}
