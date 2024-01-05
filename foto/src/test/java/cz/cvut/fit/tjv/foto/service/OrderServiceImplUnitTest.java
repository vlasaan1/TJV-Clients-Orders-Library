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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        photographerRepository.deleteAll();

        customer = new Customer();
        photographer = new Photographer();
        order = new Order();
        customer.setId(1L);
        customer.setMyOrders(new HashSet<>());
        customer.setName("testName");
        customer.setPhoneNumber("123456789");
        photographer.setId(2L);
        photographer.setName("testPhotographer");
        photographer.setSessions(new HashSet<>());
        photographer.setPhoneNumber("987654321");
        order.setId(3L);
        order.setCost(1000L);
        order.setDate("01-01-2024");
        order.setMessage("message to be kept");
        order.setAuthor(customer);
        order.setPhotographers(Collections.singleton(photographer));

        photographerRepository.save(photographer);
        customerRepository.save(customer);
        orderRepository.save(order);

//        Mockito.when(
//                orderRepository.findById(order.getId())
//        ).thenReturn(Optional.of(order));

    }


    @Test
    void createTestSuccess() {
        Order testOrder = new Order();
        testOrder.setId(5L);
        testOrder.setAuthor(customer);
        testOrder.setPhotographers(Collections.singleton(photographer));
        orderService.create(testOrder);

        Assertions.assertEquals(photographer.getSessions(), List.of(testOrder, order));
    }

    @Test
    void createTestOrderAlreadyExists(){
        //orderService.create(order);
        Mockito.when(orderRepository.existsById(order.getId())).thenReturn(true);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> orderService.create(order));
    }



}
