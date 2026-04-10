package com.lelegspears.project_wev_services.repositories;

import com.lelegspears.project_wev_services.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
