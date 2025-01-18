package dev.pablolec.webapi_service.controller;

import dev.pablolec.webapi_service.exception.UserNotFoundException;
import dev.pablolec.webapi_service.model.User;
import dev.pablolec.webapi_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> createUser(@RequestBody Mono<User> userRequest) {
        return userRequest.flatMap(userService::createUser)
                .doOnSuccess(user -> log.info("Created user: {}", user));
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with id: " + id)));
    }

    @GetMapping("/email/{email}")
    public Mono<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found with email: " + email)));
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Mono<String> handleNotFoundException(UserNotFoundException ex) {
        return Mono.just(ex.getMessage());
    }
}