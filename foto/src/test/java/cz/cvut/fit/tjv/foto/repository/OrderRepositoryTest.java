package cz.cvut.fit.tjv.foto.repository;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.domain.Photographer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;
import java.util.List;


@DataJpaTest
public class OrderRepositoryTest {
        @Autowired
        private OrderRepository orderRepository;
        @Autowired
        private CustomerRepository customerRepository;
//        @Autowired
//        private PhotographerRepository photographerRepository
        @Test
        void findByAuthorUsername() {
            var c1 = new Customer();
            var c2 = new Customer();
            var o1 = new Order();
            var o2 = new Order();
            var o3 = new Order();
//            var p1 = new Photographer();
//            var p2 = new Photographer();
            c1.setId(2L);
            c2.setId(3L);
            o1.setId(1L);
            o2.setId(2L);
            o3.setId(3L);
//            p1.setId(4L);
//            p2.setId(2L);
            o1.setAuthor(c1);
            o2.setAuthor(c1);
            o3.setAuthor(c2);
            //o3.setPhotographers((Collection<Photographer>) p1);
//        u2.setMyPosts(List.of(p3));
            customerRepository.saveAll(List.of(c1, c2));
            orderRepository.saveAll(List.of(o1, o2, o3));
//            photographerRepository.saveAll(List.of(p1, p2));

            var res = orderRepository.findByAuthorId(c1.getId());
            Assertions.assertIterableEquals(List.of(o1, o2), res);
        }


}
