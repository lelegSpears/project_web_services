package com.lelegspears.project_wev_services;

import com.lelegspears.project_wev_services.entities.User;
import com.lelegspears.project_wev_services.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
@SpringBootTest
class ProjectWevServicesApplicationTests {
    @Test
	void contextLoads() {
	}
}
