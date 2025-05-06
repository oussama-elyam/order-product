/*
//set spring.jpa.hibernate.ddl-auto=none

CREATE TABLE Actor (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       mail VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       actor_role VARCHAR(50) NOT NULL
);

CREATE TABLE product (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255) NOT NULL,
                         price VARCHAR(255) NOT NULL,
                         qte BIGINT NOT NULL,
                         status_product VARCHAR(50) NOT NULL
);

CREATE TABLE `app-order` (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             price_total VARCHAR(255) NOT NULL,
                             qte_total VARCHAR(255) NOT NULL
);

INSERT INTO product (name, price, qte, status_product) VALUES
                                                           ('Laptop', '1200.00', 10, 'AVAILABLE'),
                                                           ('Smartphone', '800.00', 25, 'AVAILABLE'),
                                                           ('Headphones', '150.00', 50, 'AVAILABLE'),
                                                           ('Monitor', '300.00', 15, 'AVAILABLE');

INSERT INTO Actor (mail, password, actor_role) VALUES
                                                   ('customer1@example.com', 'password123', 'CUSTOMER'),
                                                   ('stock1@example.com', 'stockpass', 'RESPONSABLE_STOCK'),
                                                   ('admin1@example.com', 'adminpass', 'ADMIN'),
                                                   ('customer2@example.com', 'password456', 'CUSTOMER');
*/