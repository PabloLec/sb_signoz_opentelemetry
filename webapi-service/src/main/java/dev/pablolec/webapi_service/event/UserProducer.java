package dev.pablolec.webapi_service.event;

import dev.pablolec.webapi_service.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProducer {
    private final StreamBridge streamBridge;

    public Mono<User> sendUserEvent(User user) {
        return Mono.fromRunnable(() -> {
                    Message<User> message = MessageBuilder.withPayload(user).build();
                    streamBridge.send("user-out-0", message);
                })
                .doOnSuccess(v -> log.info("Sent user event: {}", user))
                .thenReturn(user);
    }
}

