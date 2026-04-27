package com.lelegspears.project_wev_services.product.repository;

import com.lelegspears.project_wev_services.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
