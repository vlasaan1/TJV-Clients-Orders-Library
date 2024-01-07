package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.domain.Photographer;
import cz.cvut.fit.tjv.foto.repository.CustomerRepository;
import cz.cvut.fit.tjv.foto.repository.OrderRepository;
import cz.cvut.fit.tjv.foto.repository.PhotographerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootTest
public class OrderServiceImplUnitTest {

    @Autowired
    private OrderServiceImpl orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PhotographerRepository photographerRepository;
    Customer customer;
    Photographer photographer;
    Order order;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
        photographerRepository.deleteAll();
        orderRepository.deleteAll();

        customer = new Customer();
        photographer = new Photographer();
        order = new Order();

        customer.setId(1L);
        customer.setName("testName");
        customer.setPhoneNumber("123456789");
        customerRepository.save(customer);

        photographer.setId(2L);
        photographer.setName("testPhotographer");
        photographer.setPhoneNumber("987654321");
        photographerRepository.save(photographer);

        order.setId(3L);
        order.setCost(1000L);
        order.setDate("01-01-2024");
        order.setMessage("message to be kept");
        order.setAuthor(customer);
        order.setPhotographers(List.of(photographer));
        orderRepository.save(order);

        customer.setMyOrders(List.of(order));
        photographer.setSessions(List.of(order));

        customerRepository.save(customer);
        photographerRepository.save(photographer);
        orderRepository.save(order);
    }

    @Test
    @Transactional
    void deleteNotExistingOrder(){
        Customer persistedCustomer = customerRepository.findById(customer.getId()).orElseThrow();
        Photographer persistedPhotographer = photographerRepository.findById(photographer.getId()).orElseThrow();
        Order persistedOrder = orderRepository.findById(order.getId()).orElseThrow();
        // order exists before deletion
        Assertions.assertTrue(persistedCustomer.getMyOrders().contains(persistedOrder));
        Assertions.assertTrue(persistedPhotographer.getSessions().contains(persistedOrder));

        // try delete a non-existing order (ID 7L)
        orderService.deleteById(7L);

        // order(3L)is not deleted as the order(7L) does not exist
        Assertions.assertFalse(orderRepository.findById(7L).isPresent());
        Assertions.assertTrue(persistedCustomer.getMyOrders().contains(persistedOrder));
        Assertions.assertTrue(persistedPhotographer.getSessions().contains(persistedOrder));
    }

    @Test
    @Transactional
    void deleteExistingOrder() {
        Customer persistedCustomer = customerRepository.findById(customer.getId()).orElseThrow();
        Photographer persistedPhotographer = photographerRepository.findById(photographer.getId()).orElseThrow();
        Order persistedOrder = orderRepository.findById(order.getId()).orElseThrow();
        // order exists before deletion
        Assertions.assertTrue(persistedCustomer.getMyOrders().contains(persistedOrder));
        Assertions.assertTrue(persistedPhotographer.getSessions().contains(persistedOrder));

        orderService.deleteById(persistedOrder.getId());

        // order is removed after deletion
//        Assertions.assertFalse(orderRepository.findById(persistedOrder.getId()).isPresent());
//        Assertions.assertFalse(customerRepository.findById(customer.getId()).get().getMyOrders().contains(persistedOrder));
//        Assertions.assertFalse(photographerRepository.findById(photographer.getId()).get().getSessions().contains(persistedOrder));
    }


    @Test
    @Transactional
    void deleteOrderWithMultiplePhotographersAndCustomerAuthor() {
        Photographer p1 = new Photographer();
        Photographer p2 = new Photographer();
        p1.setId(3L);
        p1.setName("testPhotographer1");
        p1.setPhoneNumber("111654321");
        p2.setId(4L);
        p2.setName("testPhotographer2");
        p2.setPhoneNumber("222654321");
        photographerRepository.save(p1);
        photographerRepository.save(p2);

        Order testOrder = new Order();
        testOrder.setId(5L);
        testOrder.setAuthor(customer);
        testOrder.setPhotographers(List.of(photographer, p1, p2));
        orderService.create(testOrder);
        orderRepository.save(testOrder);

        customer.setMyOrders(List.of(order, testOrder));
        photographer.setSessions(List.of(order, testOrder));
        p1.setSessions(List.of(testOrder));
        p2.setSessions(List.of(testOrder));

        customerRepository.save(customer);
        photographerRepository.save(photographer);
        orderRepository.save(order);
    //would check if its added and if it was deleted

//        Customer persistedCustomer = customerRepository.findById(customer.getId()).orElseThrow();
//        Order persistedOrder = orderRepository.findById(testOrder.getId()).orElseThrow();
//        Assertions.assertTrue(persistedCustomer.getMyOrders().contains(persistedOrder));
//
//        Photographer photo = photographerRepository.findById(photographer.getId()).orElseThrow();
//        Assertions.assertTrue(photo.getSessions().contains(persistedOrder));
//
//        Photographer photo1 = photographerRepository.findById(photographer.getId()).orElseThrow();
//        Assertions.assertTrue(photo1.getSessions().contains(persistedOrder));
//        Photographer photo2 = photographerRepository.findById(photographer.getId()).orElseThrow();
//        Assertions.assertTrue(photo2.getSessions().contains(persistedOrder));
//
//        orderService.deleteById(testOrder.getId());
//
//
//        //deleted testOrder
//        Assertions.assertFalse(customerRepository.findById(customer.getId()).get().getMyOrders().contains(testOrder));
//        Assertions.assertFalse(photographerRepository.findById(photographer.getId()).get().getSessions().contains(testOrder));
//        Assertions.assertFalse(photographerRepository.findById(p1.getId()).get().getSessions().contains(testOrder));
//        Assertions.assertFalse(photographerRepository.findById(p2.getId()).get().getSessions().contains(testOrder));
//        //kept rest of orders
//        Assertions.assertTrue(customerRepository.findById(customer.getId()).get().getMyOrders().contains(order));
//        Assertions.assertTrue(photographerRepository.findById(photographer.getId()).get().getSessions().contains(order));
//        Assertions.assertTrue(photographerRepository.findById(p1.getId()).get().getSessions().isEmpty());
//        Assertions.assertTrue(photographerRepository.findById(p2.getId()).get().getSessions().isEmpty());

    }


}
