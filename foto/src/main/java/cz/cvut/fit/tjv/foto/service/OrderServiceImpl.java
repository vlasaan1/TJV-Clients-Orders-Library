package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.domain.Photographer;
import cz.cvut.fit.tjv.foto.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
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

//    @Override
//    public Order create(Order e) {
//        if (getRepository().existsById(e.getId())) {
//            throw new IllegalArgumentException();
//        }
//        var orders = e.getAuthor().getMyOrders();
//        if (orders.stream().anyMatch(x -> x.getAuthor().getId().equals(e.getAuthor().getId()))) {
//            throw new UnsupportedOperationException();
//        }
//        var res = getRepository().save(e);
//
//        photographerRepository.updateMyOrders(e.getAuthor().getId(), e);
//        for(Photographer photographer : e.getPhotographers()) {
//            customerRepository.updateSessions(photographer.getId(), e);
//        }
//        return res;
//
//    }

    @Override
    protected CrudRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    @Override
    public void deleteById(Long orderId) {
        Iterable<Photographer> photographers = photographerRepository.findAll();
        for (Photographer photographer: photographers) {
            photographer.getSessions().removeIf(order -> order.getId().equals(orderId));
            photographerRepository.save(photographer);
        }
        Iterable<Customer> customers = customerRepository.findAll();
        for (Customer customer: customers) {
            customer.getMyOrders().removeIf(order -> order.getId().equals(orderId));
            customerRepository.save(customer);
        }
        orderRepository.deleteById(orderId);
    }
}
