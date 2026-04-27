package com.lelegspears.project_wev_services.order.repository;

import com.lelegspears.project_wev_services.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
