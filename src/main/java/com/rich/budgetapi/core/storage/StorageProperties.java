package com.rich.budgetapi.core.storage;

import java.nio.file.Path;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties("budget.storage")
public class StorageProperties {

    private Local local = new Local();
    private StorageType type = StorageType.LOCAL;

    public enum StorageType {
        LOCAL
    }

    @Setter
    @Getter
    public class Local {
        private Path directoryPhotos;
    }
}
