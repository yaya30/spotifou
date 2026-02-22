package com.ayaco.spotifou.repository;

import com.ayaco.spotifou.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.postgresql.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;


@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {


    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    UserRepository userRepository;

    @Test
    void findByEmail_returnsUser_whenExists() {
        userRepository.saveAndFlush(User.builder()
                .email("test@gmail.com")
                .name("Test User")
                .build());

        Optional<User> result = userRepository.findByEmail("test@gmail.com");

        assertThat(result).isPresent();
        assertThat(result.get().getEmail()).isEqualTo("test@gmail.com");
    }

    @Test
    void findByEmail_returnsEmpty_whenNotExists() {
        Optional<User> result = userRepository.findByEmail("unknown@gmail.com");
        assertThat(result).isEmpty();
    }

    @Test
    void save_persistsUser_withTimestamps() {
        User user = userRepository.saveAndFlush(User.builder()
                .email("timestamps@gmail.com")
                .name("Timestamp User")
                .build());

        assertThat(user.getId()).isNotNull();
        assertThat(user.getCreatedAt()).isNotNull();
        assertThat(user.getUpdatedAt()).isNotNull();
    }

    @Test
    void save_throwsException_whenDuplicateEmail() {
        userRepository.saveAndFlush(User.builder()
                .email("duplicate@gmail.com")
                .name("First User")
                .build());

        assertThatThrownBy(() -> userRepository.saveAndFlush(User.builder()
                .email("duplicate@gmail.com")
                .name("Second User")
                .build()))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
