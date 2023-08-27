package com.newbie.springboot3gcp;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class Springboot3gcpApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot3gcpApplication.class, args);
    }

    @PostConstruct
    public void init() {
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
    }

    @Bean
    public GroupedOpenApi userOpenApi(@Value("${springdoc.version}") String appVersion) {
        String[] paths = {"/api/**"};
        return GroupedOpenApi.builder().group("users")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Users API").version(appVersion)))
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi adminOpenApi(@Value("${springdoc.version}") String appVersion) {
        String[] paths = {"/api/**", "/actuator/**"};
        return GroupedOpenApi.builder().group("admin")
                .addOpenApiCustomizer(openApi -> openApi.info(new Info().title("Admin API").version(appVersion)))
                .pathsToMatch(paths)
                .build();
    }
}
