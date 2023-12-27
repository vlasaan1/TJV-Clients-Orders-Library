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
        if(orderRepository.existsById(o.getId()) ||
                !customerRepository.existsById(o.getAuthor().getId()) ||
                o.getPhotographers().stream().allMatch(x -> photographerRepository.existsById(x.getId()))){
            throw new IllegalArgumentException();
        }
        return orderRepository.save(o);
    }



    @Override
    protected CrudRepository<Order, Long> getRepository() {
        return orderRepository;
    }

}
