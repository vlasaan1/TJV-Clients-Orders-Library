package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.repository.CustomerRepository;
import cz.cvut.fit.tjv.foto.repository.OrderRepository;
import cz.cvut.fit.tjv.foto.repository.PhotographerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

public class OrderServiceIntegrationTest {

    @SpringBootTest
    class PostServiceImplIntegrationTest {
        @Autowired
        private OrderServiceImpl orderService;
        @Autowired
        private OrderRepository orderRepository;
        @Autowired
        private CustomerRepository customerRepository;
        @Autowired
        private PhotographerRepository photographerRepository;
        Customer customer;
        Customer author;
        Order order;

        @BeforeEach
        void setUp() {
            orderRepository.deleteAll();
            customerRepository.deleteAll();
            photographerRepository.deleteAll();
            customer = new Customer();
            author = new Customer();
            order = new Order();
            customer.setId(1L);
            customer.setMyOrders(new HashSet<>());
            author.setId(2L);
            order.setId(126L);
            order.setAuthor(author);

            customerRepository.save(author);
            customerRepository.save(customer);
            orderRepository.save(order);
        }

/// operace/dotaz????



    }
