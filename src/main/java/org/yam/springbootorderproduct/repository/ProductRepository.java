package org.yam.springbootorderproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yam.springbootorderproduct.model.Order;
import org.yam.springbootorderproduct.model.Product;

@Repository

public interface ProductRepository extends JpaRepository<Product, Long> {
}
