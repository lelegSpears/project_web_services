package com.lelegspears.project_wev_services.order.repository;

import com.lelegspears.project_wev_services.order.entity.OrderItem;
import com.lelegspears.project_wev_services.order.entity.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
