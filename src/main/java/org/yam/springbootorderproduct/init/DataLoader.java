package org.yam.springbootorderproduct.init;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.yam.springbootorderproduct.model.ActorRole;
import org.yam.springbootorderproduct.model.User;
import org.yam.springbootorderproduct.repository.UserRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
//        // Creating predefined users with roles
//        User customer = new User(1L,"customer@mail.com", "password", ActorRole.CUSTOMER);
//        User responsabileStock = new User(2L,"responsable@mail.com", "password", ActorRole.RESPONSABLE_STOCK);
//        User admin = new User(3L,"admin@mail.com", "password", ActorRole.ADMIN);
//
//        // Saving users to the database
//        userRepository.saveAll(List.of(customer, responsabileStock, admin));

        System.out.println("Predefined users added!");
    }
}