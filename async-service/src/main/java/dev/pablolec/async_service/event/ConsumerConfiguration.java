package dev.pablolec.async_service.event;

import dev.pablolec.async_service.model.User;
import dev.pablolec.async_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.function.Consumer;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ConsumerConfiguration {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;

    @Bean
    public Consumer<User> userConsumer() {
        return user -> {
            try {
                log.info("User received: {}", user);
                user.setEmailSent(false);
                userRepository.save(user);

                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(user.getEmail());
                message.setSubject("Welcome " + user.getName());
                message.setText("Hello " + user.getName() + ",\n\nWelcome to our platform!");
                mailSender.send(message);
                log.info("Email sent to user: {}", user);

                user.setEmailSent(true);
                userRepository.save(user);
                log.info("User updated: {}", user);

            } catch (Exception e) {
                log.error("Error processing user: {}", user, e);
            }
        };
    }
}
