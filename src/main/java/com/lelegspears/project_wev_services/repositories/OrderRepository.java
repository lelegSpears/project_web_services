package com.lelegspears.project_wev_services.repositories;

import com.lelegspears.project_wev_services.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
