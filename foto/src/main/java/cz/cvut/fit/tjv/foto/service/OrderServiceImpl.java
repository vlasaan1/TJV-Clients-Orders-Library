package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.domain.Photographer;
import cz.cvut.fit.tjv.foto.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@Service
public class OrderServiceImpl extends CrudServiceImpl<Order, Long> implements OrderService  {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final PhotographerRepository photographerRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository, PhotographerRepository photographerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.photographerRepository = photographerRepository;
    }

    @Override
    public Collection<Order> readAllByAuthorId(Long customerId) {
        return orderRepository.findByAuthorId(customerId);
    }
    @Override
    public Collection<Order> readAllByCostBetween(Long min, Long max) {
        return orderRepository.findByCostBetween(min, max);
    }

    @Override
    protected CrudRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    @Transactional
    @Override
    public void deleteById(Long orderId) {
        Optional<Order> orderX = orderRepository.findById(orderId);
        if(orderX.isPresent()) {
            photographerRepository.deleteSessionsByOrder(orderX.get());
        }
        orderRepository.deleteById(orderId);
    }
}
