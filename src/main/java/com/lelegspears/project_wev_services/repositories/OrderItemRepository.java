package com.lelegspears.project_wev_services.repositories;

import com.lelegspears.project_wev_services.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
