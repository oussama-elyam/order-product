package org.yam.springbootorderproduct.service.implement;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.yam.springbootorderproduct.model.User;
import org.yam.springbootorderproduct.repository.UserRepository;
import org.yam.springbootorderproduct.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;

    @Override
    public User createUser(User body) {
        return userRepository.save(body);
    }
    @Override
    public Page<User> getUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    @Override
    public User updateUser(User body, Long id) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
