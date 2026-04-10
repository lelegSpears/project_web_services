package com.lelegspears.project_wev_services;

import com.lelegspears.project_wev_services.entities.Category;
import com.lelegspears.project_wev_services.entities.Product;
import com.lelegspears.project_wev_services.entities.User;
import com.lelegspears.project_wev_services.repositories.CategoryRepository;
import com.lelegspears.project_wev_services.repositories.ProductRepository;
import com.lelegspears.project_wev_services.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {

        // USERS
        User u1 = new User(null, "Leandro", "leo@email.com", "999999999", "123");
        User u2 = new User(null, "Maria", "maria@email.com", "888888888", "456");

        userRepository.saveAll(Arrays.asList(u1, u2));

        // CATEGORIES
        Category c1 = new Category();
        c1.setName("Eletrônicos");

        Category c2 = new Category();
        c2.setName("Informática");

        categoryRepository.saveAll(Arrays.asList(c1, c2));

        // PRODUCTS
        Product p1 = new Product();
        p1.setName("Notebook");
        p1.setPrice(3500.0);

        Product p2 = new Product();
        p2.setName("Mouse");
        p2.setPrice(100.0);

        // RELACIONAMENTO
        p1.addCategory(c1);
        p1.addCategory(c2);

        p2.addCategory(c2);

        productRepository.saveAll(Arrays.asList(p1, p2));
    }
}
