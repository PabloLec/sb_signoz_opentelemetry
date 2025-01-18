package dev.pablolec.webapi_service.service;

import dev.pablolec.webapi_service.event.UserProducer;
import dev.pablolec.webapi_service.model.User;
import dev.pablolec.webapi_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserProducer userProducer;

    public Mono<User> createUser(User user) {
        log.info("Creating user: {}", user);
        return userRepository
                .save(user)
                .flatMap(userProducer::sendUserEvent);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
