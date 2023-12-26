package cz.cvut.fit.tjv.foto.service;

import cz.cvut.fit.tjv.foto.domain.Customer;
import cz.cvut.fit.tjv.foto.domain.Order;
import cz.cvut.fit.tjv.foto.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class OrderServiceImpl extends CrudServiceImpl<Order, Long> implements OrderService {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Collection<Order> readAllByAuthorId(Long customerId) {
        return orderRepository.findByAuthorId(customerId);
    }

    @Override
    public void createOrder(long orderId, Long who) {
        Optional<Order> optOrder = orderRepository.findById(orderId);
        Optional<Customer> optCustomer = customerRepository.findById(who);

        if (optOrder.isEmpty() || optCustomer.isEmpty())
            throw new IllegalArgumentException("invalid ID");

        Order order = optOrder.get();
        Customer customer = optCustomer.get();
//
        if (order.getAuthor().equals(customer))
            throw new AuthorCannotCreateNewOrder();
//possibly add exception when the photographer is on the order already

//

        orderRepository.save(order);
        customerRepository.save(customer);
    }



    @Override
    protected CrudRepository<Order, Long> getRepository() {
        return orderRepository;
    }
}
