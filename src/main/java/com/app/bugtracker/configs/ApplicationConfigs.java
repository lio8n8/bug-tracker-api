package com.app.bugtracker.configs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Application configs.
 */
@Configuration
@ConfigurationProperties(prefix = "configs")
@Getter
@Setter
public class ApplicationConfigs {

    /**
     * Contains token configs.
     */
    private TokenConfigs tokenConfigs;

    /**
     * Represents token configs.
     */
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenConfigs {
        private String secretKey;
        private long expiredIn;
    }
}
