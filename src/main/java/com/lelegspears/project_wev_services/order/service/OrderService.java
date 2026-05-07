package com.lelegspears.project_wev_services.order.service;


import com.lelegspears.project_wev_services.exception.service.InvalidOrderStateException;
import com.lelegspears.project_wev_services.order.dtos.OrderCreateDTO;
import com.lelegspears.project_wev_services.order.dtos.OrderItemCreateDTO;
import com.lelegspears.project_wev_services.order.dtos.OrderResponseDTO;
import com.lelegspears.project_wev_services.order.dtos.OrderUpdateDTO;
import com.lelegspears.project_wev_services.order.entity.Order;
import com.lelegspears.project_wev_services.order.entity.OrderItem;
import com.lelegspears.project_wev_services.order.enums.OrderStatus;
import com.lelegspears.project_wev_services.order.mapper.OrderMapper;
import com.lelegspears.project_wev_services.order.repository.OrderRepository;
import com.lelegspears.project_wev_services.exception.service.DatabaseException;
import com.lelegspears.project_wev_services.exception.service.ResourceNotFoundException;
import com.lelegspears.project_wev_services.product.entity.Product;
import com.lelegspears.project_wev_services.product.repository.ProductRepository;
import com.lelegspears.project_wev_services.user.entity.User;
import com.lelegspears.project_wev_services.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, OrderMapper orderMapper, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
    }

    public OrderResponseDTO findById(Long id){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return orderMapper.toDTO(order);
    }

    public Page<OrderResponseDTO> findAll(Pageable pageable){
        Page<Order> orders = orderRepository.findAll(pageable);
        return orders.map(orderMapper::toDTO);
    }

    @Transactional
    public OrderResponseDTO insert(OrderCreateDTO dto){
        Order order = new Order();
        User client = userRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException(dto.getClientId()));
        order.setClient(client);
        order.setOrderStatus(OrderStatus.WAITING_PAYMENT);
        addOrderItems(dto.getItems(), order);
        orderRepository.save(order);
        return orderMapper.toDTO(order);
    }

    private void addOrderItems(Set<OrderItemCreateDTO> dto, Order order){
        for (OrderItemCreateDTO itemDTO : dto) {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(itemDTO.getProductId()));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setOrder(order);
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(product.getPrice());

            order.getItems().add(item);
        }
    }

    @Transactional
    public void deleteById(Long id){
        try {
            orderRepository.deleteById(id);
            orderRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integrity violation: cannot delete entity");
        }
    }

    @Transactional
    public OrderResponseDTO updateById(Long id, OrderUpdateDTO newData){
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        if(order.getOrderStatus() != OrderStatus.WAITING_PAYMENT){
            throw new InvalidOrderStateException("Orders that are already paid can't get updated.");
        }
        order.getItems().clear();
        orderRepository.flush();
        addOrderItems(newData.getItems(), order);

        return orderMapper.toDTO(order);
    }
}
