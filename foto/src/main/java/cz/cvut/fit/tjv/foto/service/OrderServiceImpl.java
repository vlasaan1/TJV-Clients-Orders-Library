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
    public Collection<Order> readAllByPhotographerId(Long photographerId){
        return orderRepository.findByPhotographers(photographerId);
    }

    @Override
    public Order createOrder(Order o) {
        Optional<Order> optOrder = orderRepository.findById(o.getId());
        Optional<Customer> optCustomer = customerRepository.findById(o.getAuthor().getId());
        Optional <Collection<Photographer>> optPhotographers = photographerRepository.findAllById(o.getPhotographers());

        if (optOrder.isEmpty() || optCustomer.isEmpty())
            throw new IllegalArgumentException("invalid ID");

        Order order = optOrder.get();
        Customer customer = optCustomer.get();
        Collection<Photographer> photographers = optPhotographers.get();

        if (order.getAuthor().equals(customer))
            throw new AuthorCannotCreateExistingOrder();
//possibly add exception when the photographer included more times?
        if (!(order.getPhotographers()).equals(photographers))
                throw new IllegalArgumentException();
        //how to check the date availability
//
        var resOrder = orderRepository.save(order);
        customerRepository.save(customer);

        for(Photographer photographer : resOrder.getPhotographers() ) {
            Collection<Order> existingSessions = photographer.getSessions();
            existingSessions.add(order);
            photographerRepository.updatePhotographerSessions(photographer, existingSessions);

        }
        return resOrder;
    }



    @Override
    protected CrudRepository<Order, Long> getRepository() {
        return orderRepository;
    }

}
