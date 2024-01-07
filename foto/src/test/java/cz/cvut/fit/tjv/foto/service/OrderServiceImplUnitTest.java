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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

@SpringBootTest
public class OrderServiceImplUnitTest {

    @Autowired
    private OrderServiceImpl orderService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private PhotographerRepository photographerRepository;
    Customer customer;
    Photographer photographer;
    Order order;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        photographer = new Photographer();
        order = new Order();

        customer.setId(1L);
        customer.setName("testName");
        customer.setPhoneNumber("123456789");

        photographer.setId(2L);
        photographer.setName("testPhotographer");
        photographer.setPhoneNumber("987654321");

        order.setId(3L);
        order.setCost(1000L);
        order.setDate("01-01-2024");
        order.setMessage("message to be kept");
        order.setAuthor(customer);
        order.setPhotographers(List.of(photographer));

        photographer.setSessions(List.of(order));
        customer.setMyOrders(List.of(order));
    }


    @Test
    void deleteExistingOrder() {
        Assertions.assertTrue(customer.getMyOrders().contains(order));
        Assertions.assertTrue(photographer.getSessions().contains(order));
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer));
        Mockito.when(photographerRepository.findAll()).thenReturn(List.of(photographer));

        orderService.deleteById(order.getId());

        Assertions.assertFalse(customer.getMyOrders().contains(order));
        Assertions.assertFalse(photographer.getSessions().contains(order));
    }

    @Test
    void deleteNotExistingOrder(){
        Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer));
        Mockito.when(photographerRepository.findAll()).thenReturn(List.of(photographer));

        orderService.deleteById(7L);
        //should only contain order, deleted nothing (because if id does not exist, it is ignored)
        Assertions.assertTrue(customer.getMyOrders().contains(order));
        Assertions.assertTrue(photographer.getSessions().contains(order));
    }

    @Test
    void deleteOrderWithMultiplePhotographersAndCustomerAuthor() {
        Photographer p1 = new Photographer();
        Photographer p2 = new Photographer();
        p1.setId(3L);
        p1.setName("testPhotographer1");
        p1.setPhoneNumber("111654321");
        p2.setId(4L);
        p2.setName("testPhotographer2");
        p2.setPhoneNumber("222654321");

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

        Mockito.when(customerRepository.findAll()).thenReturn(List.of(customer));
        Mockito.when(photographerRepository.findAll()).thenReturn(List.of(photographer, p1, p2));

        //contain testOrder
        Assertions.assertTrue(customer.getMyOrders().contains(testOrder));
        Assertions.assertTrue(photographer.getSessions().contains(testOrder));
        Assertions.assertTrue(p1.getSessions().contains(testOrder));
        Assertions.assertTrue(p2.getSessions().contains(testOrder));

        orderService.deleteById(testOrder.getId());

        //deleted testOrder
        Assertions.assertFalse(customer.getMyOrders().contains(testOrder));
        Assertions.assertFalse(photographer.getSessions().contains(testOrder));
        Assertions.assertFalse(p1.getSessions().contains(testOrder));
        Assertions.assertFalse(p2.getSessions().contains(testOrder));
        //kept rest of orders
        Assertions.assertTrue(customer.getMyOrders().contains(order));
        Assertions.assertTrue(photographer.getSessions().contains(order));
        Assertions.assertTrue(p1.getSessions().isEmpty());
        Assertions.assertTrue(p2.getSessions().isEmpty());

    }


}
