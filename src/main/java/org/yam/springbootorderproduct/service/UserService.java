package org.yam.springbootorderproduct.service;

import org.springframework.data.domain.Page;
import org.yam.springbootorderproduct.model.User;

public interface UserService {
    User createUser(User body);
    Page<User> getUsers(int page, int size);
    User updateUser(User body, Long id);
    void deleteUser(Long id);
}
