package cz.cvut.fit.tjv.foto.repository;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.domain.Photographer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;
import java.util.Collections;
import java.util.List;


@DataJpaTest
public class OrderRepositoryTest {
        @Autowired
        private OrderRepository orderRepository;
        @Autowired
        private CustomerRepository customerRepository;
        @Test
        void findByCostBetweenTest() {
            var c1 = new Customer();
            var c2 = new Customer();
            var o1 = new Order();
            var o2 = new Order();
            var o3 = new Order();
            var o4 = new Order();
            c1.setId(2L);
            c2.setId(3L);
            o1.setId(1L);
            o1.setCost(10L);
            o2.setId(2L);
            o2.setCost(20L);
            o3.setId(3L);
            o3.setCost(30L);
            o4.setId(4L);
            o4.setCost(40L);
            o1.setAuthor(c1);
            o2.setAuthor(c1);
            o3.setAuthor(c2);
            customerRepository.saveAll(List.of(c1, c2));
            orderRepository.saveAll(List.of(o1, o2, o3, o4));
            var res = orderRepository.findByCostBetween(15L, 35L);

            Assertions.assertIterableEquals(List.of(o2, o3), res);
        }

}
