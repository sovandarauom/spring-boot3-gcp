package com.newbie.springboot3gcp.handler;

import com.newbie.springboot3gcp.entity.User;
import com.newbie.springboot3gcp.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component

public class UserHandler {


    private final UserService userService;

    public UserHandler(UserService userService) {
        this.userService = userService;
    }


    @PreAuthorize("hasRole('USER')")
    public Mono<ServerResponse> findById(ServerRequest request) {
        var id = request.pathVariable("id");
        return userService.findById(id)
                .flatMap(person -> ServerResponse.ok().bodyValue(person))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.findAll(), User.class);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> findByCountry(ServerRequest request) {
        var country = request.pathVariable("country");
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userService.findByCountry(country), User.class);
    }

    @PreAuthorize("hasRole('USER')")
    public Mono<ServerResponse> save(ServerRequest request) {
        var domain = request.bodyToMono(User.class);
        return domain.flatMap(userService::save)
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user));

    }

    @PreAuthorize("hasRole('USER')")
    public Mono<ServerResponse> update(ServerRequest request) {
        var id = request.pathVariable("id");
        var domain = request.bodyToMono(User.class);

        return domain.flatMap(user -> userService.update(id, user))
                .flatMap(user -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(user));

    }

    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ServerResponse> delete(ServerRequest request) {

        var id = request.pathVariable("id");
        return userService.delete(id)
                .flatMap(user -> ServerResponse.ok().build());

    }
}
