package com.newbie.springboot3gcp.config;

import com.newbie.springboot3gcp.handlers.CustomAccessDeniedHandler;
import com.newbie.springboot3gcp.handlers.CustomAuthenticationEntryPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.UUID;

@Configuration
@EnableWebFluxSecurity
@Slf4j
public class SecurityConfig {

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        var password = UUID.randomUUID().toString();
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("springboot3gcp")
                .password(password)
                .roles("USER")
                .build();
        log.info("Temporary password: {}", password);
        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/api/v1/**").authenticated()
                        .anyExchange().permitAll()
                )
                .httpBasic(httpBasicSpec -> httpBasicSpec.authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .exceptionHandling(exceptionHandlingSpec ->
                        exceptionHandlingSpec.accessDeniedHandler(new CustomAccessDeniedHandler())
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                )
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable);
        return http.build();
    }
}
