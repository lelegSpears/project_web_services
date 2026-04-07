package com.lelegspears.project_wev_services;

import com.lelegspears.project_wev_services.entities.User;
import com.lelegspears.project_wev_services.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private final UserRepository userRepository;

    public TestConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User u1 = new User(null, "Charlie Brown", "charlie@gmail", "11999999999", "123456");
        User u2 = new User(null, "Maria Silva", "maria@gmail", "11888888888", "654321");

        userRepository.saveAll(Arrays.asList(u1, u2));
    }
}
