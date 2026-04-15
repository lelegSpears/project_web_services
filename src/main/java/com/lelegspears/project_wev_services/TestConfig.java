package com.lelegspears.project_wev_services;

import com.lelegspears.project_wev_services.entities.*;
import com.lelegspears.project_wev_services.entities.enums.OrderStatus;
import com.lelegspears.project_wev_services.repositories.*;
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
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) throws Exception {

        // USER
        User u1 = new User(null, "Leandro", "leo@email.com", "11999999999", "123456");

        // CATEGORY
        Category c1 = new Category("Eletrônicos");
        Category c2 = new Category("Games");

        categoryRepository.saveAll(Arrays.asList(c1, c2));

        // PRODUCT
        Product p1 = new Product();
        p1.setName("PS5");
        p1.setDescription("Console");
        p1.setPrice(4500.0);
        p1.setImgURL("img1");

        Product p2 = new Product();
        p2.setName("TV");
        p2.setDescription("Smart TV");
        p2.setPrice(3000.0);
        p2.setImgURL("img2");

        p1.addCategory(c1);
        p1.addCategory(c2);
        p2.addCategory(c1);

        productRepository.saveAll(Arrays.asList(p1, p2));

        // USER + ORDER
        userRepository.save(u1);

        Order o1 = new Order(null, Instant.now(), u1, OrderStatus.WAITING_PAYMENT);

        orderRepository.save(o1);

        // ORDER ITEM
        OrderItem oi1 = new OrderItem(p1, o1, 1, p1.getPrice());
        OrderItem oi2 = new OrderItem(p2, o1, 2, p2.getPrice());

        orderItemRepository.saveAll(Arrays.asList(oi1, oi2));

        Payment pay1 = new Payment(null, Instant.parse("2026-06-20T21:20:10Z"), o1);
        o1.setPayment(pay1);
        orderRepository.save(o1);
    }
}
