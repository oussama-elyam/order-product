package org.yam.springbootorderproduct.service.implement;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.yam.springbootorderproduct.model.Order;
import org.yam.springbootorderproduct.repository.OrderRepository;
import org.yam.springbootorderproduct.service.OrderService;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order createOrder(Order body) {
        return orderRepository.save(body);
    }
    @Override
    public Page<Order> getOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return orderRepository.findAll(pageable);
    }

    @Override
    public Order updateOrder(Order body, Long id) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
