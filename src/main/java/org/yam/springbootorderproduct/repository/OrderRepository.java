package org.yam.springbootorderproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yam.springbootorderproduct.model.Order;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
