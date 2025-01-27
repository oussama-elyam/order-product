package org.yam.springbootorderproduct.service;

import org.springframework.data.domain.Page;
import org.yam.springbootorderproduct.model.Order;

public interface OrderService {
    Order createOrder(Order body);
    Page<Order> getOrders(int page, int size);
    Order updateOrder(Order body, Long id);
    void deleteOrder(Long id);
}
