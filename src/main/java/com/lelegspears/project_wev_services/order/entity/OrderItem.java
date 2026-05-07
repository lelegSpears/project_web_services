package com.lelegspears.project_wev_services.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lelegspears.project_wev_services.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(name = "tb_order_item")
public class OrderItem implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @JsonIgnore
    @Setter(AccessLevel.NONE)
    private OrderItemPK id;

    private Integer quantity;
    private BigDecimal price;

    public OrderItem() {
    }

    public OrderItem(Product product, Order order, Integer quantity, BigDecimal price) {
        this.id = new OrderItemPK(order,product);
        this.quantity = quantity;
        this.price = price;
    }

    @JsonIgnore
    public Order getOrder(){
        return id.getOrder();
    }

    public void setOrder(Order order){
        this.id.setOrder(order);
    }

    public Product getProduct(){
        return id.getProduct();
    }

    public void setProduct(Product product){
        this.id.setProduct(product);
    }

    public BigDecimal getSubTotal() {
        return this.price.multiply(BigDecimal.valueOf(this.quantity));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderItem orderItem = (OrderItem) o;
        return Objects.equals(id, orderItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
