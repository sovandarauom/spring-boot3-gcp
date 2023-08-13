package com.newbie.springboot3gcp.controller;

import com.newbie.springboot3gcp.entity.User;
import com.newbie.springboot3gcp.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@SecurityRequirement(name = "auth")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<User> save(@RequestBody User user) {

        return userService.save(user);
    }

    @DeleteMapping("/users/{id}")
    private Mono<ResponseEntity<String>> delete(
            @PathVariable String id) {

        return userService.delete(id)
                .flatMap(user -> Mono.just(ResponseEntity
                        .ok("Deleted Successfully")))
                .switchIfEmpty(Mono.just(ResponseEntity
                        .notFound().build()));

    }

    @PutMapping("/users/{id}")
    private Mono<ResponseEntity<User>> update(
            @PathVariable String id,
            @RequestBody User user) {

        return userService.update(id, user)
                .flatMap(user1 -> Mono.just(ResponseEntity
                        .ok(user1)))
                .switchIfEmpty(Mono
                        .just(ResponseEntity.notFound().build()));

    }

    @GetMapping(value = "/users")
    private Flux<User> findAll() {

        return userService.findAll();
    }

    @GetMapping(value = "/users/{id}")
    private Mono<User> findUserById(@PathVariable String id) {

        return userService.findById(id);
    }

    @GetMapping(value = "/users/country/{name}")
    private Flux<User> findAllUsersByCountry(
            @PathVariable String name) {

        return userService.findByCountry(name);
    }
}
