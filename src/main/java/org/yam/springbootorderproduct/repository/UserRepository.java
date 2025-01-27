package org.yam.springbootorderproduct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.yam.springbootorderproduct.model.User;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
}
