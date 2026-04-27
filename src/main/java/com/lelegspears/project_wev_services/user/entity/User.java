    package com.lelegspears.project_wev_services.user.entity;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonProperty;
    import com.lelegspears.project_wev_services.order.entity.Order;
    import jakarta.persistence.*;
    import jakarta.validation.constraints.Email;
    import jakarta.validation.constraints.NotBlank;
    import jakarta.validation.constraints.Size;
    import lombok.AccessLevel;
    import lombok.Getter;
    import lombok.Setter;

    import java.io.Serial;
    import java.io.Serializable;
    import java.util.ArrayList;
    import java.util.List;
    import java.util.Objects;

    @Setter
    @Getter
    @Entity
    @Table(name = "tb_user")
    public class User implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Setter(AccessLevel.NONE)
        private Long id;

        @NotBlank
        @Size(max = 55)
        private String name;

        @Email
        private String email;

        @NotBlank
        private String phone;

        @NotBlank
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private String password;

        @Setter(AccessLevel.NONE)
        @JsonIgnore
        @OneToMany(mappedBy = "client")
        private List<Order> orders = new ArrayList<>();

        public User(){
        }

        public User(Long id, String name, String email, String phone, String password) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.phone = phone;
            this.password = password;
        }

        public void addOrder(Order order) {
            orders.add(order);
            order.setClient(this);
        }

        public void removeOrder(Order order) {
            orders.remove(order);
            order.setClient(null);
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) return false;
            User user = (User) o;
            return Objects.equals(id, user.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }
    }
