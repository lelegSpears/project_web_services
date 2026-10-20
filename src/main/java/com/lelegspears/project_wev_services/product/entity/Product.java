package com.lelegspears.project_wev_services.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lelegspears.project_wev_services.category.entity.Category;
import com.lelegspears.project_wev_services.exception.service.StockMaxExceededException;
import com.lelegspears.project_wev_services.order.entity.Order;
import com.lelegspears.project_wev_services.order.entity.OrderItem;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    @Setter(AccessLevel.NONE)
    @PositiveOrZero
    private Integer stockQuantity = 0;

    private String imgURL;

    @Setter(AccessLevel.NONE)
    @ManyToMany
    @JoinTable(name= "tb_product_category", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name= "category_id"))
    private Set<Category> categories = new HashSet<>();

    @Getter(AccessLevel.NONE)
    @OneToMany(mappedBy = "id.product")
    private Set<OrderItem> items = new HashSet<>();

    public void addCategory(Category category) {
        categories.add(category);
        category.getProducts().add(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.getProducts().remove(this);
    }

    public void addStock(Integer quantity) {
        if(quantity == null) {
            throw new IllegalArgumentException("Quantity must not be null");
        }
        if(quantity <= 0){
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if(quantity + this.stockQuantity >10000) {
            throw new StockMaxExceededException("Total Quantity must not exceed 10000");
        }
        this.stockQuantity += quantity;
    }

    @JsonIgnore
    public Set<Order> getOrders(){
        Set<Order> set = new HashSet<>();
        for(OrderItem x : items){
            set.add(x.getOrder());
        }
        return set;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
