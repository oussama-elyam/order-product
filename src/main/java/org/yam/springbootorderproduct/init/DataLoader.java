package org.yam.springbootorderproduct.init;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.yam.springbootorderproduct.model.ActorRole;
import org.yam.springbootorderproduct.model.Actor;
import org.yam.springbootorderproduct.repository.ActorRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final ActorRepository actorRepository;

    @Override
    public void run(String... args) throws Exception {
        // Creating predefined actors with roles
        Actor customer = new Actor(null,"customer@mail.com", "password", ActorRole.CUSTOMER);
        Actor responsabileStock = new Actor(null,"responsable@mail.com", "password", ActorRole.RESPONSABLE_STOCK);
        Actor admin = new Actor(null,"admin@mail.com", "password", ActorRole.ADMIN);

        // Saving actors to the database
        actorRepository.saveAll(List.of(customer, responsabileStock, admin));

        System.out.println("Predefined actors added!");
    }
}