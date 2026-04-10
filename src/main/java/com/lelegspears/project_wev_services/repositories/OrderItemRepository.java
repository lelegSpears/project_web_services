package com.lelegspears.project_wev_services.repositories;

import com.lelegspears.project_wev_services.entities.OrderItem;
import com.lelegspears.project_wev_services.entities.pk.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
