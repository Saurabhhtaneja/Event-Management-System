package com.example.eventmngmt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig.java
 * ─────────────────────────────────────────────────────────────────
 * ADD THIS FILE to: src/main/java/com/example/eventmngmt/config/
 *
 * This is an ADDITIONAL CORS fix using WebMvcConfigurer.
 * Keep your existing SecurityConfig.java AND add this file.
 * Having both ensures CORS works regardless of request type.
 * ─────────────────────────────────────────────────────────────────
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")           // apply to ALL endpoints
                .allowedOriginPatterns("*")  // allow ALL origins
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}